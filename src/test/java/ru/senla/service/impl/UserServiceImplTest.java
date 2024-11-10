package ru.senla.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.senla.data.UserTestData;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.exception.InvalidEmailException;
import ru.senla.exception.InvalidPasswordException;
import ru.senla.mapper.UserMapper;
import ru.senla.model.entity.User;
import ru.senla.repository.api.UserRepository;
import ru.senla.service.api.TokenService;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
  private static final String MOCKED_TOKEN = "MOCKED_TOKEN";
  private static final String ENCODED_PASSWORD = "ENCODED_PASSWORD";
  private static final String EXCEPTION_ERROR = "ERROR";
  private static final String INVALID_PASSWORD = "INVALID";
  @InjectMocks private UserServiceImpl userService;

  @Mock private UserMapper userMapper;

  @Mock private UserRepository userRepository;

  @Mock private TokenService tokenService;

  @Mock private PasswordEncoder passwordEncoder;

  @Nested
  class GetAuthorizationToken {

    @Test
    void shouldReturnTokenWhenCredentialsAreValid() {
      // given
      var userRequest = UserTestData.builder().build().buildUserRequest();
      var user = UserTestData.builder().build().buildUser();

      when(userRepository.findByEmail(userRequest.email())).thenReturn(Optional.of(user));
      when(passwordEncoder.matches(userRequest.password(), user.getPassword())).thenReturn(true);
      when(tokenService.generateToken(user)).thenReturn(MOCKED_TOKEN);

      // when
      var token = userService.getAuthorizationToken(userRequest);

      // then
      assertEquals(MOCKED_TOKEN, token);
      verify(userRepository).findByEmail(userRequest.email());
      verify(passwordEncoder).matches(userRequest.password(), user.getPassword());
      verify(tokenService).generateToken(user);
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailIsNotFound() {
      // given
      var userRequest = UserTestData.builder().build().buildUserRequest();
      when(userRepository.findByEmail(userRequest.email())).thenReturn(Optional.empty());

      // when & then
      assertThrows(
          InvalidEmailException.class, () -> userService.getAuthorizationToken(userRequest));
      verify(userRepository).findByEmail(userRequest.email());
      verifyNoInteractions(passwordEncoder, tokenService);
    }
  }

  @Nested
  class LoadUserByUsername {

    @Test
    void shouldReturnUserDetailsWhenUserIsFound() {
      // given
      var user = UserTestData.builder().build().buildUser();

      when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

      // when
      var userDetails = userService.loadUserByUsername(user.getEmail());

      // then
      assertEquals(user.getEmail(), userDetails.getUsername());
      verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUserIsNotFound() {
      // given
      var email = UserTestData.builder().build().buildUser().getEmail();
      when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

      // when & then
      assertThrows(EntityNotFoundException.class, () -> userService.loadUserByUsername(email));
      verify(userRepository).findByEmail(email);
    }
  }

  @Nested
  class Create {

    @Test
    void shouldReturnUserResponseWhenUserIsCreatedSuccessfully() {
      // given
      var userRequest = UserTestData.builder().build().buildUserRequest();
      var user = UserTestData.builder().build().buildUser();
      var userResponse = UserTestData.builder().build().buildUserResponse();

      when(userMapper.toUser(userRequest)).thenReturn(user);
      when(passwordEncoder.encode(userRequest.password())).thenReturn(ENCODED_PASSWORD);
      when(userRepository.save(any(User.class))).thenReturn(user);
      when(userMapper.toUserResponse(user)).thenReturn(userResponse);

      // when
      var result = userService.create(userRequest);

      // then
      assertEquals(userResponse, result);
      verify(userMapper).toUser(userRequest);
      verify(passwordEncoder).encode(userRequest.password());
      verify(userRepository).save(user);
      verify(userMapper).toUserResponse(user);
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenDataAccessExceptionOccurs() {
      // given
      var userRequest = UserTestData.builder().build().buildUserRequest();
      var user = UserTestData.builder().build().buildUser();

      when(userMapper.toUser(userRequest)).thenReturn(user);
      when(passwordEncoder.encode(userRequest.password())).thenReturn(ENCODED_PASSWORD);
      when(userRepository.save(any(User.class)))
          .thenThrow(new DataAccessException(EXCEPTION_ERROR) {});

      // when & then
      assertThrows(InvalidEmailException.class, () -> userService.create(userRequest));
      verify(userMapper).toUser(userRequest);
      verify(passwordEncoder).encode(userRequest.password());
      verify(userRepository).save(user);
    }

    @Test
    void shouldInvalidPasswordExceptionWhenPasswordInvalid() {
      // given
      var userRequest =
          UserTestData.builder().withPassword(INVALID_PASSWORD).build().buildUserRequest();
      var user = UserTestData.builder().build().buildUser();

      // when
      when(userRepository.findByEmail(userRequest.email())).thenReturn(Optional.of(user));
      when(passwordEncoder.matches(userRequest.password(), user.getPassword())).thenReturn(false);

      // then
      assertThrows(
          InvalidPasswordException.class, () -> userService.getAuthorizationToken(userRequest));
      verify(tokenService, never()).generateToken(any());
    }
  }
}
