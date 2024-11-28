package ru.senla.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.senla.exception.InvalidTokenException;
import ru.senla.model.entity.User;
import ru.senla.service.api.TokenService;

/**
 * The JwtTokenServiceImpl class implements the TokenService interface to provide functionality for
 * generating and validating JSON Web Tokens (JWT).
 */
@Service
public class JwtTokenServiceImpl implements TokenService {

  @Value("${spring.security.secret}")
  private String secret;

  @Value("${spring.security.issuer}")
  private String issuer;

  /**
   * Generates a JWT for the given user.
   *
   * @param user the user for whom the token is generated
   * @return a signed JWT as a string
   */
  @Override
  public String generateToken(User user) {
    var authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    return Jwts.builder()
        .setSubject(user.getEmail())
        .setIssuer(issuer)
        .claim("authorities", authorities)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
        .signWith(getSignInKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  /**
   * Retrieves the email from the given JWT.
   *
   * @param token the JWT to extract the email from
   * @return the email contained in the JWT
   * @throws InvalidTokenException if the token is invalid or expired
   */
  @Override
  public String getEmail(String token) {
    checkToken(token);
    return getClaims(token).getSubject();
  }

  /**
   * Retrieves the authorities from the given JWT.
   *
   * @param token the JWT to extract the authorities from
   * @return a list of authorities contained in the JWT
   * @throws InvalidTokenException if the token is invalid or expired
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<String> getAuthorities(String token) {
    checkToken(token);
    return getClaims(token).get("authorities", List.class);
  }

  /**
   * Validates the provided JWT token, checking its expiration.
   *
   * @param token the JWT to validate
   * @throws InvalidTokenException if the token is expired or invalid
   */
  private void checkToken(String token) {
    boolean isValid;
    try {
      isValid = !getClaims(token).getExpiration().before(new Date());
    } catch (JwtException exception) {
      isValid = false;
    }
    if (!isValid) {
      throw new InvalidTokenException();
    }
  }

  /**
   * Parses the claims from the provided JWT.
   *
   * @param token the JWT to parse claims from
   * @return the claims contained in the JWT
   */
  private Claims getClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .requireIssuer(issuer)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * Retrieves the signing key from the base64-encoded secret.
   *
   * @return the signing key used for token signing
   */
  private Key getSignInKey() {
    byte[] decode = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(decode);
  }
}
