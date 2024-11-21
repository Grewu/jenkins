package service.impl;

import dao.api.UserDao;
import data.UserTestData;
import exception.EntityNotFoundException;
import exception.InvalidEmailException;
import mapper.UserMapper;
import model.entity.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import service.api.TokenService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserDao userDao;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final String MOCKED_TOKEN = "mocked-token";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    @Nested
    class GetAuthorizationToken {

        @Test
        void shouldReturnTokenWhenCredentialsAreValid() {
            // given
            var userRequest = UserTestData.builder().build().buildUserRequest();
            var user = UserTestData.builder().build().buildUser();

            when(userDao.findByEmail(userRequest.email())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(userRequest.password(), user.getPassword())).thenReturn(true);
            when(tokenService.generateToken(user)).thenReturn(MOCKED_TOKEN);

            // when
            var token = userService.getAuthorizationToken(userRequest);

            // then
            assertEquals(MOCKED_TOKEN, token);
            verify(userDao).findByEmail(userRequest.email());
            verify(passwordEncoder).matches(userRequest.password(), user.getPassword());
            verify(tokenService).generateToken(user);
        }

        @Test
        void shouldThrowInvalidEmailExceptionWhenEmailIsNotFound() {
            // given
            var userRequest = UserTestData.builder().build().buildUserRequest();
            when(userDao.findByEmail(userRequest.email())).thenReturn(Optional.empty());

            // when & then
            assertThrows(InvalidEmailException.class, () -> userService.getAuthorizationToken(userRequest));
            verify(userDao).findByEmail(userRequest.email());
            verifyNoInteractions(passwordEncoder, tokenService);
        }
    }

    @Nested
    class LoadUserByUsername {

        @Test
        void shouldReturnUserDetailsWhenUserIsFound() {
            // given
            var user = UserTestData.builder().build().buildUser();

            when(userDao.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

            // when
            var userDetails = userService.loadUserByUsername(user.getEmail());

            // then
            assertEquals(user.getEmail(), userDetails.getUsername());
            verify(userDao).findByEmail(user.getEmail());
        }

        @Test
        void shouldThrowEntityNotFoundExceptionWhenUserIsNotFound() {
            // given
            var email = UserTestData.builder().build().buildUser().getEmail();
            when(userDao.findByEmail(email)).thenReturn(Optional.empty());

            // when & then
            assertThrows(EntityNotFoundException.class, () -> userService.loadUserByUsername(email));
            verify(userDao).findByEmail(email);
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
            when(userDao.create(any(User.class))).thenReturn(user);
            when(userMapper.toUserResponse(user)).thenReturn(userResponse);

            // when
            var result = userService.create(userRequest);

            // then
            assertEquals(userResponse, result);
            verify(userMapper).toUser(userRequest);
            verify(passwordEncoder).encode(userRequest.password());
            verify(userDao).create(user);
            verify(userMapper).toUserResponse(user);
        }

        @Test
        void shouldThrowInvalidEmailExceptionWhenDataAccessExceptionOccurs() {
            // given
            var userRequest = UserTestData.builder().build().buildUserRequest();
            var user = UserTestData.builder().build().buildUser();

            when(userMapper.toUser(userRequest)).thenReturn(user);
            when(passwordEncoder.encode(userRequest.password())).thenReturn(ENCODED_PASSWORD);
            when(userDao.create(any(User.class))).thenThrow(new DataAccessException("Database error") {
            });

            // when & then
            assertThrows(InvalidEmailException.class, () -> userService.create(userRequest));
            verify(userMapper).toUser(userRequest);
            verify(passwordEncoder).encode(userRequest.password());
            verify(userDao).create(user);
        }
    }
}
