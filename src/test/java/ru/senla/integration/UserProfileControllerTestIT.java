package ru.senla.integration;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.senla.data.UserProfileTestData;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.service.api.UserProfileService;
import ru.senla.util.IntegrationTest;
import ru.senla.util.PostgresqlTestContainer;

@IntegrationTest
@AutoConfigureMockMvc
class UserProfileControllerTestIT extends PostgresqlTestContainer {
  private static final String URL = "/api/v0/user_profiles";
  private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";

  @Autowired private MockMvc mockMvc;
  @Autowired private UserProfileService userProfileService;

  @Nested
  class Create {
    @Test
    @WithMockUser(authorities = {"user_profile:write"})
    void createShouldReturnUserProfileResponse() throws Exception {
      // given
      var userProfileRequest = UserProfileTestData.builder().build().buildUserProfileRequest();
      var requestBody =
          """
                    {
                        "firstName": "firstName",
                        "lastName": "lastName",
                        "position": 1,
                        "department": 1,
                        "user": 1
                    }
                    """;

      // when
      mockMvc
          .perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(requestBody))
          // then
          .andExpect(status().isCreated())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json(requestBody))
          .andDo(print());

      assertThatCode(() -> userProfileService.create(userProfileRequest))
          .doesNotThrowAnyException();
    }

    @Test
    void createShouldReturnForbidden() throws Exception {
      // given
      var requestBody =
          """
                    {
                        "firstName": "firstName",
                        "lastName": "lastName",
                        "positionId": 1,
                        "departmentId": 1,
                        "userId": 1
                    }
                    """;

      // when
      mockMvc
          .perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(requestBody))
          // then
          .andExpect(status().isForbidden());
    }
  }

  @Nested
  class GetAll {
    @Test
    @WithMockUser(authorities = {"user_profile:read"})
    void getAllShouldReturnListOfUserProfileResponses() throws Exception {
      // given
      var pageable = Pageable.ofSize(2);
      var expectedResponses =
          List.of(
              UserProfileTestData.builder().build().buildUserProfileResponse(),
              UserProfileTestData.builder().withId(2L).build().buildUserProfileResponse(),
              UserProfileTestData.builder().withId(3L).build().buildUserProfileResponse(),
              UserProfileTestData.builder().withId(4L).build().buildUserProfileResponse());

      // when
      mockMvc
          .perform(get(URL).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.content").isNotEmpty())
          .andExpect(jsonPath("$.content.size()").value(expectedResponses.size()));

      assertThatCode(() -> userProfileService.getAll(pageable)).doesNotThrowAnyException();

      var actualResponse = userProfileService.getAll(pageable).stream().toList();

      IntStream.range(0, actualResponse.size())
          .forEach(i -> assertThat(actualResponse.get(i)).isEqualTo(expectedResponses.get(i)));
    }

    @Test
    void getAllShouldReturnForbidden() throws Exception {
      // when
      mockMvc
          .perform(get(URL).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());
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

      // when
      mockMvc
          .perform(
              get(URL_WITH_PARAMETER_ID, userProfileId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(
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

      assertThatCode(() -> userProfileService.getById(userProfileId)).doesNotThrowAnyException();
    }

    @Test
    void getByIdShouldReturnForbidden() throws Exception {
      // given
      var userProfileResponse = UserProfileTestData.builder().build().buildUserProfileResponse();
      var userProfileId = userProfileResponse.id();

      // when
      mockMvc
          .perform(
              get(URL_WITH_PARAMETER_ID, userProfileId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      assertThatCode(() -> userProfileService.getById(userProfileId)).doesNotThrowAnyException();
    }
  }

  @Nested
  class Update {
    @Test
    @WithMockUser(authorities = {"user_profile:write"})
    void updateShouldReturnUpdatedUserProfileResponse() throws Exception {
      // given
      var userProfileId = UserProfileTestData.builder().build().buildUserProfile().getId();
      var userProfileRequest =
          UserProfileTestData.builder()
              .withFirstName("UpdatedName")
              .withLastName("UpdatedLastName")
              .build()
              .buildUserProfileRequest();

      var expectedResponse =
          UserProfileTestData.builder()
              .withFirstName("UpdatedName")
              .withLastName("UpdatedLastName")
              .build()
              .buildUserProfileResponse();

      var requestBody =
          """
                    {
                        "firstName": "UpdatedName",
                        "lastName": "UpdatedLastName",
                        "position": 1,
                        "department": 1,
                        "user": 1
                    }
                    """;

      // when
      mockMvc
          .perform(
              put(URL_WITH_PARAMETER_ID, userProfileId)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(requestBody))
          // then
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(
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

      assertThatCode(() -> userProfileService.update(userProfileId, userProfileRequest))
          .doesNotThrowAnyException();

      var actualResponse = userProfileService.getById(userProfileId);
      assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void updateShouldReturnForbidden() throws Exception {
      // given
      var userProfileId = UserProfileTestData.builder().build().buildUserProfile().getId();

      // when
      mockMvc
          .perform(
              put(URL_WITH_PARAMETER_ID, userProfileId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());
    }
  }

  @Nested
  class Delete {
    @Test
    @WithMockUser(authorities = {"user_profile:delete"})
    void deleteShouldReturnNoContentThrowEntityNotFoundException() throws Exception {
      // given
      var userProfileResponse = UserProfileTestData.builder().build().buildUserProfileResponse();
      var userProfileId = userProfileResponse.id();

      // when
      mockMvc
          .perform(
              delete(URL_WITH_PARAMETER_ID, userProfileId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isNoContent());

      assertThatThrownBy(() -> userProfileService.delete(userProfileId))
          .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteShouldReturnForbidden() throws Exception {
      // given
      var userProfileId = UserProfileTestData.builder().build().buildUserProfileResponse().id();

      // when
      mockMvc
          .perform(
              delete(URL_WITH_PARAMETER_ID, userProfileId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());
    }
  }
}
