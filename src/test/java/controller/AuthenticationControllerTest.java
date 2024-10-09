package controller;

import config.SecurityConfig;
import config.TestContainersConfiguration;
import data.UserTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.api.UserService;
import util.IntegrationTest;
import util.PostgresqlTestContainer;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {TestContainersConfiguration.class, SecurityConfig.class})
class AuthenticationControllerTest extends PostgresqlTestContainer {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private static final String URL_REGISTER = "/api/v0/auth/register";
    private static final String URL_AUTHENTICATE = "/api/v0/auth/authenticate";
    private static final String EXPECTED_TOKEN = "expectedToken";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authenticationController)
                .build();
    }

    @Test
    void registerShouldReturnAuthorizationToken() throws Exception {
        // given
        var userRequest = UserTestData.builder().build().buildUserRequest();


        doReturn(EXPECTED_TOKEN).when(userService).getAuthorizationToken(userRequest);

        var requestBuilder = post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "user@example.com",
                            "password": "password",
                            "role": 1
                        }
                        """);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().string(EXPECTED_TOKEN)
                );
    }

    @Test
    void authenticateShouldReturnUserResponse() throws Exception {
        // given
        var userRequest = UserTestData.builder().build().buildUserRequest();
        var expectedResponse = UserTestData.builder().build().buildUserResponse();

        doReturn(expectedResponse).when(userService).create(userRequest);

        var requestBuilder = post(URL_AUTHENTICATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "user@example.com",
                            "password": "password",
                            "role": 1
                        }
                        """);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "email": "user@example.com",
                                    "role": 1
                                }
                                """)
                );
    }
}