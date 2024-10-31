package ru.senla.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.senla.config.ExceptionHandlerConfig;
import ru.senla.exception.ExceptionMessage;
import ru.senla.util.ControllerFake;
import ru.senla.util.DtoFake;

@WebMvcTest
@ContextConfiguration(classes = {ExceptionHandlerConfig.class, ControllerFake.class})
@TestPropertySource(properties = "spring.exception.handling.enabled=true")
public class GlobalHandlerAdviceTestIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper mapper;

  private static final String URL = "/fake";

  private static final String NOT_FOUND_MESSAGE = "DtoFake with ID %d was not found";
  private static final String INVALID_EMAIL_MESSAGE = "Invalid email: %s";
  private static final String INVALID_TOKEN_MESSAGE = "Invalid token";
  private static final String INVALID_PASSWORD_MESSAGE = "Invalid password: %s";
  private static final String BAD_REQUEST_MESSAGE = "id = must be greater than 0";

  @Test
  void handleEntityNotFoundException() throws Exception {
    var id = 1L;
    var exceptionMessage =
        new ExceptionMessage(HttpStatus.NOT_FOUND, String.format(NOT_FOUND_MESSAGE, id));

    mockMvc
        .perform(get(URL + "/entity/{id}", 1L))
        .andExpectAll(
            status().isNotFound(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(mapper.writeValueAsString(exceptionMessage)));
  }

  @Test
  void handleInvalidEmailException() throws Exception {
    var invalidEmail = "invalidEmail";
    var exceptionMessage =
        new ExceptionMessage(
            HttpStatus.UNAUTHORIZED, String.format(INVALID_EMAIL_MESSAGE, invalidEmail));

    mockMvc
        .perform(get(URL + "/email/{email}", invalidEmail))
        .andExpectAll(
            status().isUnauthorized(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(mapper.writeValueAsString(exceptionMessage)));
  }

  @Test
  void handleInvalidTokenException() throws Exception {
    var exceptionMessage = new ExceptionMessage(HttpStatus.UNAUTHORIZED, INVALID_TOKEN_MESSAGE);

    mockMvc
        .perform(post(URL + "/token"))
        .andExpectAll(
            status().isUnauthorized(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(mapper.writeValueAsString(exceptionMessage)));
  }

  @Test
  void handleInvalidPasswordException() throws Exception {
    var invalidPassword = "invalidPassword";
    var exceptionMessage =
        new ExceptionMessage(
            HttpStatus.UNAUTHORIZED, String.format(INVALID_PASSWORD_MESSAGE, invalidPassword));

    mockMvc
        .perform(post(URL + "/password/{password}", invalidPassword))
        .andExpectAll(
            status().isUnauthorized(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(mapper.writeValueAsString(exceptionMessage)));
  }

  @Test
  void handleMethodArgumentNotValidException() throws Exception {
    var dtoFake = new DtoFake(-1L);
    var exceptionMessage = new ExceptionMessage(HttpStatus.BAD_REQUEST, BAD_REQUEST_MESSAGE);
    var expectedJson = mapper.writeValueAsString(exceptionMessage);

    mockMvc
        .perform(
            post(URL + "/valid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoFake)))
        .andExpectAll(
            status().isBadRequest(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().json(expectedJson));
  }
}
