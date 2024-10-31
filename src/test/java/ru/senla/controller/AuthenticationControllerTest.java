package ru.senla.controller;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.senla.data.UserTestData;
import ru.senla.service.api.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserService userService;

  private static final String URL_REGISTER = "/api/v0/auth/register";
  private static final String URL_AUTHENTICATE = "/api/v0/auth/authenticate";
  private static final String EXPECTED_TOKEN = "expectedToken";

  @Test
  void registerShouldReturnAuthorizationToken() throws Exception {
    // given
    var userRequest = UserTestData.builder().build().buildUserRequest();

    doReturn(EXPECTED_TOKEN).when(userService).getAuthorizationToken(userRequest);

    var requestBuilder =
        post(URL_REGISTER)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                        {
                            "email": "user@example.com",
                            "password": "password",
                            "role": 1
                        }
                        """);

    // when
    mockMvc
        .perform(requestBuilder)
        // then
        .andExpectAll(
            status().isCreated(),
            content().contentType(MediaType.APPLICATION_JSON),
            content().string(EXPECTED_TOKEN));
  }

  @Test
  void authenticateShouldReturnUserResponse() throws Exception {
    // given
    var userRequest = UserTestData.builder().build().buildUserRequest();
    var expectedResponse = UserTestData.builder().build().buildUserResponse();

    doReturn(expectedResponse).when(userService).create(userRequest);

    var requestBuilder =
        post(URL_AUTHENTICATE)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                        {
                            "email": "user@example.com",
                            "password": "password",
                            "role": 1
                        }
                        """);

    // when
    mockMvc
        .perform(requestBuilder)
        // then
        .andExpectAll(
            status().isCreated(),
            content().contentType(MediaType.APPLICATION_JSON),
            content()
                .json(
                    """
                                {
                                    "id": 1,
                                    "email": "user@example.com",
                                    "role": 1
                                }
                                """));
  }
}
