package ru.senla.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.senla.data.UserProfileTestData;
import ru.senla.service.api.UserProfileService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    private static final String URL = "/api/v0/user_profiles";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";

    @Nested
    class Create {
        @Test
        @WithMockUser(authorities = {"user_profile:write"})
        void createShouldReturnUserProfileResponse() throws Exception {
            // given
            var userProfileRequest = UserProfileTestData.builder().build().buildUserProfileRequest();
            var expectedResponse = UserProfileTestData.builder().build().buildUserProfileResponse();

            doReturn(expectedResponse)
                    .when(userProfileService).create(userProfileRequest);

            var requestBuilder = post(URL)
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
                                        "firstName": "firstName",
                                        "lastName": "lastName",
                                        "position": 1,
                                        "department": 1,
                                        "user": 1
                                    }
                                    """)
                    );

            verify(userProfileService).create(any());
        }

        @Test
        void createShouldReturnForbidden() throws Exception {
            // given
            var requestBuilder = post(URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                                "firstName": "firstName",
                                "lastName": "lastName",
                                "positionId": 1,
                                "departmentId": 1,
                                "userId": 1
                            }
                            """);

            // when
            mockMvc.perform(requestBuilder)
                    // then
                    .andExpect(status().isForbidden());

            verify(userProfileService, never()).create(any());
        }
    }

    @Nested
    class GetAll {
        @Test
        @WithMockUser(authorities = {"user_profile:read"})
        void getAllShouldReturnListOfUserProfileResponses() throws Exception {
            // given
            var pageable = Pageable.ofSize(2);
            var expectedResponses = List.of(
                    UserProfileTestData.builder().build().buildUserProfileResponse(),
                    UserProfileTestData.builder().withId(2L).build().buildUserProfileResponse()
            );

            when(userProfileService.getAll(any(Pageable.class)))
                    .thenReturn(new PageImpl<>(expectedResponses, pageable, 2));

            // when
            mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content.size()").value(expectedResponses.size()))
                    .andExpect(jsonPath("$.content[0].id").value(expectedResponses.get(0).id()))
                    .andExpect(jsonPath("$.content[0].firstName").value(expectedResponses.get(0).firstName()))
                    .andExpect(jsonPath("$.content[0].lastName").value(expectedResponses.get(0).lastName()))
                    .andExpect(jsonPath("$.content[0].position").value(expectedResponses.get(0).position()))
                    .andExpect(jsonPath("$.content[0].department").value(expectedResponses.get(0).department()))
                    .andExpect(jsonPath("$.content[0].user").value(expectedResponses.get(0).user()))
                    .andExpect(jsonPath("$.content[1].id").value(expectedResponses.get(1).id()))
                    .andExpect(jsonPath("$.content[1].firstName").value(expectedResponses.get(1).firstName()))
                    .andExpect(jsonPath("$.content[1].lastName").value(expectedResponses.get(1).lastName()))
                    .andExpect(jsonPath("$.content[1].position").value(expectedResponses.get(1).position()))
                    .andExpect(jsonPath("$.content[1].department").value(expectedResponses.get(1).department()))
                    .andExpect(jsonPath("$.content[1].user").value(expectedResponses.get(1).user()));
        }

        @Test
        void getAllShouldReturnForbidden() throws Exception {
            //given
            var pageable = Pageable.ofSize(2);
            // when
            mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isForbidden());

            verify(userProfileService, never()).getAll(pageable);
        }
    }

    @Nested
    class GetByID {
        @Test
        @WithMockUser(authorities = {"user_profile:read"})
        void getByIdShouldReturnUserProfileResponse() throws Exception {
            // given
            var userProfileResponse = UserProfileTestData.builder().build().buildUserProfileResponse();
            var userProfileId = userProfileResponse.id();

            doReturn(userProfileResponse)
                    .when(userProfileService).getById(userProfileId);

            // when
            mockMvc.perform(get(URL_WITH_PARAMETER_ID, userProfileId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpectAll(
                            status().isOk(),
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
            verify(userProfileService).getById(any());
        }

        @Test
        void getByIdShouldReturnForbidden() throws Exception {
            // given
            var userProfileResponse = UserProfileTestData.builder().build().buildUserProfileResponse();
            var userProfileId = userProfileResponse.id();

            doReturn(userProfileResponse)
                    .when(userProfileService).getById(userProfileId);

            // when
            mockMvc.perform(get(URL_WITH_PARAMETER_ID, userProfileId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isForbidden());

            verify(userProfileService, never()).getById(any());
        }
    }

    @Nested
    class Update {
        @Test
        @WithMockUser(authorities = {"user_profile:write"})
        void updateShouldReturnUpdatedUserProfileResponse() throws Exception {
            // given
            var userProfileId = 1L;
            var userProfileRequest = UserProfileTestData.builder()
                    .withFirstName("UpdatedName")
                    .withLastName("UpdatedLastName")
                    .build().buildUserProfileRequest();

            var updatedResponse = UserProfileTestData.builder()
                    .withFirstName("UpdatedName")
                    .withLastName("UpdatedLastName")
                    .build().buildUserProfileResponse();

            doReturn(updatedResponse).when(userProfileService).update(userProfileId, userProfileRequest);

            var requestBuilder = put(URL_WITH_PARAMETER_ID, userProfileId)
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

            verify(userProfileService).update(any(), any());
        }

        @Test
        void updateShouldReturnForbidden() throws Exception {
            // given
            var userProfileId = 1L;

            // when
            mockMvc.perform(put(URL_WITH_PARAMETER_ID, userProfileId))
                    // then
                    .andExpect(status().isForbidden());

            verify(userProfileService, never()).update(any(), any());
        }
    }

    @Nested
    class Delete {
        @Test
        @WithMockUser(authorities = {"user_profile:delete"})
        void deleteShouldReturnNoContent() throws Exception {
            // given
            var userProfileId = 1L;

            // when
            mockMvc.perform(delete(URL_WITH_PARAMETER_ID, userProfileId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isNoContent());

            verify(userProfileService).delete(userProfileId);
        }

        @Test
        void deleteShouldReturnForbidden() throws Exception {
            // given
            var userProfileId = 1L;

            // when
            mockMvc.perform(delete(URL_WITH_PARAMETER_ID, userProfileId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isForbidden());

            verify(userProfileService, never()).delete(userProfileId);
        }
    }
}
