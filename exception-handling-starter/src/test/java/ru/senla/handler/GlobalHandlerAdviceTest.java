package ru.senla.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.senla.config.ExceptionHandlerConfig;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.exception.InvalidEmailException;
import ru.senla.exception.InvalidPasswordException;
import ru.senla.exception.InvalidTokenException;
import ru.senla.util.ControllerFake;
import ru.senla.util.MockEntity;

@WebMvcTest
@ContextConfiguration(classes = {ExceptionHandlerConfig.class, ControllerFake.class})
@TestPropertySource(properties = "spring.exception.handling.enabled=true")
class GlobalHandlerAdviceTest {

  private final GlobalHandlerAdvice globalHandlerAdvice = new GlobalHandlerAdvice();

  @Test
  void handleEntityNotFoundException() {
    var entityNotFoundException = new EntityNotFoundException(MockEntity.class, 1L);
    var handle = globalHandlerAdvice.handle(entityNotFoundException);

    assertThat(handle)
        .hasFieldOrPropertyWithValue("statusCode", HttpStatus.NOT_FOUND)
        .extracting("body")
        .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND)
        .hasFieldOrPropertyWithValue("message", "MockEntity with ID 1 was not found");
  }

  @Test
  void handleInvalidEmailException() {
    var invalidEmail = "invalidEmail";
    var invalidEmailException = new InvalidEmailException(invalidEmail);

    var handle = globalHandlerAdvice.handle(invalidEmailException);

    assertThat(handle)
        .hasFieldOrPropertyWithValue("statusCode", HttpStatus.UNAUTHORIZED)
        .extracting("body")
        .hasFieldOrPropertyWithValue("status", HttpStatus.UNAUTHORIZED)
        .hasFieldOrPropertyWithValue("message", "Invalid email: invalidEmail");
  }

  @Test
  void handleInvalidTokenException() {
    var invalidTokenException = new InvalidTokenException();

    var handle = globalHandlerAdvice.handle(invalidTokenException);

    assertThat(handle)
        .hasFieldOrPropertyWithValue("statusCode", HttpStatus.UNAUTHORIZED)
        .extracting("body")
        .hasFieldOrPropertyWithValue("status", HttpStatus.UNAUTHORIZED)
        .hasFieldOrPropertyWithValue("message", "Invalid token");
  }

  @Test
  void handleInvalidPasswordException() {
    var invalidPassword = "invalidPassword";
    var invalidPasswordException = new InvalidPasswordException(invalidPassword);

    var handle = globalHandlerAdvice.handle(invalidPasswordException);

    assertThat(handle)
        .hasFieldOrPropertyWithValue("statusCode", HttpStatus.UNAUTHORIZED)
        .extracting("body")
        .hasFieldOrPropertyWithValue("status", HttpStatus.UNAUTHORIZED)
        .hasFieldOrPropertyWithValue("message", "Invalid password: invalidPassword");
  }
}
