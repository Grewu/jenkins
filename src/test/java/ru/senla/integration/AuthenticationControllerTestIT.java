package ru.senla.integration;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.senla.data.UserTestData;
import ru.senla.service.api.UserService;
import ru.senla.util.IntegrationTest;
import ru.senla.util.PostgresqlTestContainer;

@IntegrationTest
@AutoConfigureMockMvc
class AuthenticationControllerTestIT extends PostgresqlTestContainer {

  @Autowired private MockMvc mockMvc;

  @Autowired private UserService userService;

  private static final String URL_REGISTER = "/api/v0/auth/register";

  private static final String JWT_REGEX =
      "^([a-zA-Z\\d_=]+)\\.([a-zA-Z\\d_=]+)\\.([a-zA-Z\\d_\\-+/=]*)";

  @Test
  void registerShouldReturnAuthorizationToken() throws Exception {
    // given
    var userRequest = UserTestData.builder().build().buildUserRequest();
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
    var actualResponse =
        mockMvc
            .perform(requestBuilder)
            // then
            .andExpectAll(status().isCreated(), content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertThatCode(() -> userService.getAuthorizationToken(userRequest)).doesNotThrowAnyException();
    assertThat(actualResponse).isNotEmpty();
    assertThat(actualResponse).matches(JWT_REGEX);
  }
}
