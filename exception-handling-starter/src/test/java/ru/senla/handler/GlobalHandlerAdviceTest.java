package ru.senla.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.senla.config.ExceptionHandlerConfig;
import ru.senla.exception.*;
import ru.senla.util.ControllerFake;
import ru.senla.util.MockEntity;

@WebMvcTest
@ContextConfiguration(classes = {ExceptionHandlerConfig.class, ControllerFake.class})
@TestPropertySource(properties = "spring.exception.handling.enabled=true")
class GlobalHandlerAdviceTest {

  private static final Long MOCK_ENTITY_ID = 1L;
  private static final String INVALID_EMAIL = "invalidEmail";
  private static final String INVALID_PASSWORD = "invalidPassword";
  private static final String ENTITY_NOT_FOUND_MESSAGE =
      "MockEntity with ID " + MOCK_ENTITY_ID + " was not found";
  private static final String INVALID_EMAIL_MESSAGE = "Invalid email: " + INVALID_EMAIL;
  private static final String INVALID_TOKEN_MESSAGE = "Invalid token";
  private static final String INVALID_PASSWORD_MESSAGE = "Invalid password: " + INVALID_PASSWORD;
  private static final String ENTITY_ALREADY_EXISTS_MESSAGE =
      "MockEntity with ID " + MOCK_ENTITY_ID + " already exists";

  private final GlobalHandlerAdvice handlerAdvice = new GlobalHandlerAdvice();

  @Test
  void handleEntityNotFoundException_shouldReturnNotFoundStatus() {
    var exception = new EntityNotFoundException(MockEntity.class, MOCK_ENTITY_ID);
    var response = handlerAdvice.handle(exception);

    assertThat(response)
        .hasFieldOrPropertyWithValue("statusCode", NOT_FOUND)
        .extracting("body")
        .hasFieldOrPropertyWithValue("status", NOT_FOUND)
        .hasFieldOrPropertyWithValue("message", ENTITY_NOT_FOUND_MESSAGE);
  }

  @Test
  void handleInvalidEmailException_shouldReturnUnauthorizedStatus() {
    var exception = new InvalidEmailException(INVALID_EMAIL);

    var response = handlerAdvice.handle(exception);

    assertThat(response)
        .hasFieldOrPropertyWithValue("statusCode", UNAUTHORIZED)
        .extracting("body")
        .hasFieldOrPropertyWithValue("status", UNAUTHORIZED)
        .hasFieldOrPropertyWithValue("message", INVALID_EMAIL_MESSAGE);
  }

  @Test
  void handleInvalidTokenException_shouldReturnUnauthorizedStatus() {
    var exception = new InvalidTokenException();
    var response = handlerAdvice.handle(exception);

    assertThat(response)
        .hasFieldOrPropertyWithValue("statusCode", UNAUTHORIZED)
        .extracting("body")
        .hasFieldOrPropertyWithValue("status", UNAUTHORIZED)
        .hasFieldOrPropertyWithValue("message", INVALID_TOKEN_MESSAGE);
  }

  @Test
  void handleInvalidPasswordException_shouldReturnUnauthorizedStatus() {
    var exception = new InvalidPasswordException(INVALID_PASSWORD);

    var response = handlerAdvice.handle(exception);

    assertThat(response)
        .hasFieldOrPropertyWithValue("statusCode", UNAUTHORIZED)
        .extracting("body")
        .hasFieldOrPropertyWithValue("status", UNAUTHORIZED)
        .hasFieldOrPropertyWithValue("message", INVALID_PASSWORD_MESSAGE);
  }

  @Test
  void handleEntityAlreadyExistsException_shouldReturnConflictStatus() {
    var exception = new EntityAlreadyExistsException(MockEntity.class, MOCK_ENTITY_ID);

    var response = handlerAdvice.handle(exception);

    assertThat(response)
        .hasFieldOrPropertyWithValue("statusCode", CONFLICT)
        .extracting("body")
        .hasFieldOrPropertyWithValue("status", CONFLICT)
        .hasFieldOrPropertyWithValue("message", ENTITY_ALREADY_EXISTS_MESSAGE);
  }
}
