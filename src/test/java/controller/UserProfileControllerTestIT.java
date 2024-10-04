package controller;

import config.TestContainersConfiguration;
import data.UserProfileTestData;
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
import service.api.UserProfileService;
import util.IntegrationTest;
import util.PostgresqlTestContainer;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestContainersConfiguration.class)
class UserProfileControllerTestIT extends PostgresqlTestContainer {

    private MockMvc mockMvc;

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private UserProfileController userProfileController;

    private static final String URL = "/api/v0/user_profiles";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";
    private static final String URL_CRITERIA = URL + "/criteria/{id}";
    private static final String URL_JPQL = URL + "/jpql/{id}";
    private static final String URL_ENTITY_GRAPH = URL + "/entitygraph/{id}";


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userProfileController)
                .build();
    }

    @Test
    void getByIdShouldReturnExpectedResponseEntityUserProfileResponse() throws Exception {
        //given
        var userProfile = UserProfileTestData.builder().build().buildUserProfile();
        var requestBuilder = get(URL_WITH_PARAMETER_ID, userProfile.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstName": "name",
                            "lastName": "name",
                            "position": 1,
                            "department": 2,
                            "user": 3
                        }
                            """);
        //when
        mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }

    @Test
    void getAllShouldReturnListOfUserProfileResponse() throws Exception {
        // given
        var expectedResponses = List.of(
                UserProfileTestData.builder().build().buildUserProfileResponse(),
                UserProfileTestData.builder().withId(2L).build().buildUserProfileResponse()
        );

        doReturn(expectedResponses).when(userProfileService).getAll();

        // when
        mockMvc.perform(get(UserProfileController.USER_PROFILE_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                  {
                                    "id": 1,
                                    "firstName": "firstName",
                                    "lastName": "lastName",
                                    "position": 1,
                                    "department": 1,
                                    "user": 1
                                  },
                                  {
                                    "id": 2,
                                    "firstName": "firstName",
                                    "lastName": "lastName",
                                    "position": 1,
                                    "department": 1,
                                    "user": 1
                                  }
                                ]
                                """)
                );
    }

    @Test
    void createShouldReturnUserProfileResponse() throws Exception {
        // given
        var userProfileRequest = UserProfileTestData.builder().build().buildUserProfileRequest();
        var expected = UserProfileTestData.builder().build().buildUserProfileResponse();

        doReturn(expected).when(userProfileService).create(userProfileRequest);

        var requestBuilder = post(UserProfileController.USER_PROFILE_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstName": "firstName",
                            "lastName": "lastName",
                            "position": 1,
                            "department": 1,
                            "user": 1
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
                                    "firstName": "firstName",
                                    "lastName": "lastName",
                                    "position": 1,
                                    "department": 1,
                                    "user": 1
                                }
                                """)
                );
    }


    @Test
    void updateShouldReturnUpdatedUserProfileResponse() throws Exception {
        // given
        var userProfileRequest = UserProfileTestData.builder()
                .withFirstName("UpdatedName")
                .withLastName("UpdatedLastName")
                .build().buildUserProfileRequest();

        var updatedResponse = UserProfileTestData.builder()
                .withFirstName("UpdatedName")
                .withLastName("UpdatedLastName")
                .build().buildUserProfileResponse();

        doReturn(updatedResponse).when(userProfileService).update(1L, userProfileRequest);

        var requestBuilder = put(UserProfileController.USER_PROFILE_API_PATH + "/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstName": "UpdatedName",
                            "lastName": "UpdatedLastName",
                            "position": 1,
                            "department": 1,
                            "user": 1
                        }
                        """);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "firstName": "UpdatedName",
                                    "lastName": "UpdatedLastName",
                                    "position": 1,
                                    "department": 1,
                                    "user": 1
                                }
                                """)
                );
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        // given
        var userProfileRequest = UserProfileTestData.builder().build().buildUserProfileRequest();

        // when
        mockMvc.perform(delete(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "firstName",
                                    "lastName": "lastName",
                                    "position": 1,
                                    "department": 1,
                                    "user": 1
                                }
                                """))
                // then
                .andExpect(status().isNoContent());

        verify(userProfileService).delete(userProfileRequest);
    }

    @Test
    void findUserProfilesWithUserDepartmentAndPositionCriteriaAPIShouldReturnExpectedResponse() throws Exception {
        // given
        Long id = 1L;
        var expectedResponse = UserProfileTestData.builder().build().buildUserProfileResponse();

        when(userProfileService.findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(id))
                .thenReturn(expectedResponse);

        // when
        mockMvc.perform(get(URL_CRITERIA, id)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "id": 1,
                          "firstName": "firstName",
                          "lastName": "lastName",
                          "position": 1,
                          "department": 1,
                          "user": 1
                        }
                        """));

        verify(userProfileService).findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(id);
    }

    @Test
    void findUserProfilesWithUserDepartmentAndPositionJpqlShouldReturnExpectedResponse() throws Exception {
        Long id = 2L;
        var expectedResponse = UserProfileTestData.builder().withId(2L).build().buildUserProfileResponse();

        when(userProfileService.findUserProfilesWithUserDepartmentAndPositionJPQL(id))
                .thenReturn(expectedResponse);

        // when
        mockMvc.perform(get(URL_JPQL, id)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "id": 2,
                          "firstName": "firstName",
                          "lastName": "lastName",
                          "position": 1,
                          "department": 1,
                          "user": 1
                        }
                        """));

        verify(userProfileService).findUserProfilesWithUserDepartmentAndPositionJPQL(id);
    }

    @Test
    void findUserProfilesWithUserDepartmentAndPositionEntityGraphShouldReturnExpectedResponse() throws Exception {
        // given
        Long id = 3L;
        var expectedResponse = UserProfileTestData.builder().withId(3L).build().buildUserProfileResponse();

        when(userProfileService.findUserProfilesWithUserDepartmentAndPositionEntityGraph(id))
                .thenReturn(expectedResponse);

        // when
        mockMvc.perform(get(URL_ENTITY_GRAPH, id)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "id": 3,
                          "firstName": "firstName",
                          "lastName": "lastName",
                          "position": 1,
                          "department": 1,
                          "user": 1
                        }
                        """));

        verify(userProfileService).findUserProfilesWithUserDepartmentAndPositionEntityGraph(id);
    }

}