package ru.senla.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.senla.data.UserTestData;
import ru.senla.exception.InvalidEmailException;
import ru.senla.repository.api.UserRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JwtTokenServiceImpl.class)
@TestPropertySource(
    properties = {
      "spring.security.secret=244226452948404D6351655468576D5A7134743777217A25432A462D4A614E645267556A586E"
          + "3272357538782F413F4428472B4B6250655368566D5970337336d",
      "spring.security.issuer=test"
    })
class JwtTokenServiceImplTest {

  @Value("${spring.security.secret}")
  private String secret;

  @Value("${spring.security.issuer}")
  private String issuer;

  @Autowired private JwtTokenServiceImpl tokenService;

  @Mock private UserRepository userRepository;

  @InjectMocks private UserServiceImpl userService;

  private static final String INVALID_TOKEN = "invalid-token";

  @Test
  void generateTokenShouldReturnValidToken() {
    // given
    var user = UserTestData.builder().build().buildUser();

    // when
    var token = tokenService.generateToken(user);
    var decode = Decoders.BASE64.decode(secret);

    var claims =
        Jwts.parserBuilder()
            .requireIssuer(issuer)
            .setSigningKey(Keys.hmacShaKeyFor(decode))
            .build()
            .parseClaimsJws(token)
            .getBody();

    // then
    assertTrue(claims.getExpiration().after(new Date()));
    assertEquals(user.getEmail(), claims.getSubject());
    assertEquals(issuer, claims.getIssuer());
    assertNotNull(claims.getIssuedAt());
    assertTrue(claims.getIssuedAt().before(new Date()));
  }

  @Test
  void getUserNameShouldReturnCorrectUserName() {
    // given
    var user = UserTestData.builder().build().buildUser();

    // when
    var token = tokenService.generateToken(user);
    var email = tokenService.getEmail(token);

    // then
    assertEquals(user.getEmail(), email);
  }

  @ParameterizedTest
  @CsvSource({"not_existed_email,password"})
  void getAuthorizationTokenThrowsInvalidEmailException(String email, String password) {
    // given
    var userRequest =
        UserTestData.builder().withEmail(email).withPassword(password).build().buildUserRequest();

    // when & then
    assertThrows(InvalidEmailException.class, () -> userService.getAuthorizationToken(userRequest));
  }

  @Test
  void checkAuthorities() {
    // given
    var user = UserTestData.builder().build().buildUser();

    // when
    var token = tokenService.generateToken(user);
    var authorities = tokenService.getAuthorities(token);

    // then
    var userAuthorities =
        user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    assertEquals(authorities, userAuthorities);
  }
}
