package ru.senla.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
import ru.senla.data.CommentTestData;
import ru.senla.data.UserProfileTestData;
import ru.senla.service.api.UserProfileService;

@SpringBootTest
@AutoConfigureMockMvc
class UserProfileControllerTest {
  private static final String URL = "/api/v0/user_profiles";
  private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";
  private static final String URL_WITH_USERPROFILE_ID = URL + "/{userProfileId}/comments";
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private UserProfileService userProfileService;

  @Nested
  class Create {
    @Test
    @WithMockUser(authorities = {"user_profile:write"})
    void createShouldReturnUserProfileResponse() throws Exception {
      // given
      var userProfileRequest = UserProfileTestData.builder().build().buildUserProfileRequest();
      var expectedResponse = UserProfileTestData.builder().build().buildUserProfileResponse();

      doReturn(expectedResponse).when(userProfileService).create(userProfileRequest);

      var requestBuilder =
          post(URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                                            {
                                                "firstName": "firstName",
                                                "lastName": "lastName",
                                                "position": 1,
                                                "department": 1,
                                                "user": 1
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
                                                        "firstName": "firstName",
                                                        "lastName": "lastName",
                                                        "position": 1,
                                                        "department": 1,
                                                        "user": 1
                                                    }
                                                    """));

      verify(userProfileService).create(any());
    }

    @Test
    void createShouldReturnForbidden() throws Exception {
      // given
      var requestBuilder =
          post(URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                                            {
                                                "firstName": "firstName",
                                                "lastName": "lastName",
                                                "positionId": 1,
                                                "departmentId": 1,
                                                "userId": 1
                                            }
                                            """);

      // when
      mockMvc
          .perform(requestBuilder)
          // then
          .andExpect(status().isForbidden());

      verify(userProfileService, never()).create(any());
    }
  }

  @Nested
  class GetAll {
    @Test
    @WithMockUser(authorities = {"user_profile:read", "comments:read"})
    void getCommentsByProfileIdShouldReturnPageOfCommentsResponse() throws Exception {
      // given
      var pageable = Pageable.ofSize(2);
      var userProfileId = UserProfileTestData.builder().build().buildUserProfile().getId();

      var expectedResponses =
          List.of(
              CommentTestData.builder().build().buildCommentResponse(),
              CommentTestData.builder().withId(2L).build().buildCommentResponse());

      var expectedPage = new PageImpl<>(expectedResponses, pageable, expectedResponses.size());

      when(userProfileService.getAllCommentsByProfileId(anyLong(), any(Pageable.class)))
          .thenReturn(expectedPage);

      // when
      mockMvc
          .perform(
              get(URL_WITH_USERPROFILE_ID, userProfileId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json(objectMapper.writeValueAsString(expectedPage)));
    }

    @Test
    @WithMockUser(authorities = {"user_profile:read"})
    void getAllShouldReturnListOfUserProfileResponses() throws Exception {
      // given
      var pageable = Pageable.ofSize(2);
      var expectedResponses =
          List.of(
              UserProfileTestData.builder().build().buildUserProfileResponse(),
              UserProfileTestData.builder().withId(2L).build().buildUserProfileResponse());

      var expectedPage = new PageImpl<>(expectedResponses, pageable, expectedResponses.size());

      when(userProfileService.getAll(any(Pageable.class))).thenReturn(expectedPage);

      // when
      mockMvc
          .perform(get(URL).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json(objectMapper.writeValueAsString(expectedPage)));
    }

    @Test
    void getAllShouldReturnForbidden() throws Exception {
      // given
      var pageable = Pageable.ofSize(2);
      // when
      mockMvc
          .perform(get(URL).contentType(MediaType.APPLICATION_JSON))
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

      doReturn(userProfileResponse).when(userProfileService).getById(userProfileId);

      // when
      mockMvc
          .perform(
              get(URL_WITH_PARAMETER_ID, userProfileId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      """
                                                    {
                                                        "id": 1,
                                                        "firstName": "firstName",
                                                        "lastName": "lastName",
                                                        "position": 1,
                                                        "department": 1,
                                                        "user": 1
                                                    }
                                                    """));
      verify(userProfileService).getById(any());
    }

    @Test
    void getByIdShouldReturnForbidden() throws Exception {
      // given
      var userProfileResponse = UserProfileTestData.builder().build().buildUserProfileResponse();
      var userProfileId = userProfileResponse.id();

      doReturn(userProfileResponse).when(userProfileService).getById(userProfileId);

      // when
      mockMvc
          .perform(
              get(URL_WITH_PARAMETER_ID, userProfileId).contentType(MediaType.APPLICATION_JSON))
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
      var userProfileRequest =
          UserProfileTestData.builder()
              .withFirstName("UpdatedName")
              .withLastName("UpdatedLastName")
              .build()
              .buildUserProfileRequest();

      var updatedResponse =
          UserProfileTestData.builder()
              .withFirstName("UpdatedName")
              .withLastName("UpdatedLastName")
              .build()
              .buildUserProfileResponse();

      doReturn(updatedResponse).when(userProfileService).update(userProfileId, userProfileRequest);

      var requestBuilder =
          put(URL_WITH_PARAMETER_ID, userProfileId)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                                              {
                                                 "firstName": "UpdatedName",
                                                 "lastName": "UpdatedLastName",
                                                 "position": 1,
                                                  "department": 1,
                                                  "user": 1
                                               }
                                            """);

      // when
      mockMvc
          .perform(requestBuilder)
          // then
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      """
                                                     {
                                                       "id": 1,
                                                       "firstName": "UpdatedName",
                                                       "lastName": "UpdatedLastName",
                                                       "position": 1,
                                                       "department": 1,
                                                       "user": 1
                                                      }
                                                    """));

      verify(userProfileService).update(any(), any());
    }

    @Test
    void updateShouldReturnForbidden() throws Exception {
      // given
      var userProfileId = 1L;

      // when
      mockMvc
          .perform(put(URL_WITH_PARAMETER_ID, userProfileId))
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
      mockMvc
          .perform(
              delete(URL_WITH_PARAMETER_ID, userProfileId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isNoContent());

      verify(userProfileService).delete(userProfileId);
    }

    @Test
    void deleteShouldReturnForbidden() throws Exception {
      // given
      var userProfileId = 1L;

      // when
      mockMvc
          .perform(
              delete(URL_WITH_PARAMETER_ID, userProfileId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(userProfileService, never()).delete(userProfileId);
    }
  }
}
