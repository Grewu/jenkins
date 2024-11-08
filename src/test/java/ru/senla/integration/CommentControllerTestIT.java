package ru.senla.integration;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.senla.data.CommentTestData;
import ru.senla.service.api.CommentService;
import ru.senla.util.IntegrationTest;
import ru.senla.util.PostgresqlTestContainer;

@IntegrationTest
@AutoConfigureMockMvc
class CommentControllerTestIT extends PostgresqlTestContainer {
  private static final String URL = "/api/v0/comments";
  private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";
  @Autowired private MockMvc mockMvc;

  @Autowired private CommentService commentService;

  @Nested
  class Create {

    @Test
    @WithMockUser(authorities = {"comments:write"})
    void createShouldReturnCommentResponse() throws Exception {
      // given
      var commentRequest = CommentTestData.builder().build().buildCommentRequest();
      var requestBuilder =
          post(URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                            {
                                "task": 1,
                                "usersProfile": 1,
                                "commentText": "commentText",
                                "createdAt": "2024-09-30T12:00:00"
                            }
                            """);

      // when
      mockMvc
          .perform(
              requestBuilder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpectAll(
              status().isCreated(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      """
                                    {
                                        "task": 1,
                                        "usersProfile": 1,
                                        "commentText": "commentText",
                                        "createdAt": "2024-09-30T12:00:00"
                                    }
                                    """));

      assertThatCode(() -> commentService.create(commentRequest)).doesNotThrowAnyException();
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
                                "task": 1,
                                "usersProfile": 1,
                                "commentText": "commentText",
                                "createdAt": "2024-09-30T12:00:00"
                            }
                            """);

      // when
      mockMvc
          .perform(
              requestBuilder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
          // then
          .andExpect(status().isForbidden());
    }
  }

  @Nested
  class GetAll {

    @Test
    @WithMockUser(authorities = {"comments:read"})
    void getAllShouldReturnListOfCommentResponses() throws Exception {
      // given
      var pageable = Pageable.ofSize(2);

      var expectedResponses =
          List.of(
              CommentTestData.builder().build().buildCommentResponse(),
              CommentTestData.builder().withId(2L).build().buildCommentResponse(),
              CommentTestData.builder().withId(3L).build().buildCommentResponse(),
              CommentTestData.builder().withId(4L).build().buildCommentResponse());

      // when
      mockMvc
          .perform(get(URL).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpectAll(status().isOk(), content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.content").isNotEmpty())
          .andExpect(jsonPath("$.content.size()").value(expectedResponses.size()));

      assertThatCode(() -> commentService.getAll(pageable)).doesNotThrowAnyException();

      var actualResponse = commentService.getAll(pageable).stream().toList();

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
    @WithMockUser(authorities = {"comments:read"})
    void getByIdShouldReturnCommentResponse() throws Exception {
      // given
      var expectedResponse = CommentTestData.builder().build().buildCommentResponse();
      var commentId = expectedResponse.id();

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, commentId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      """
                                    {
                                        "task": 1,
                                        "usersProfile": 1,
                                        "commentText": "commentText",
                                        "createdAt": "2024-09-30T12:00:00"
                                    }
                                    """));

      assertThatCode(() -> commentService.getById(commentId)).doesNotThrowAnyException();

      var actualResponse = commentService.getById(commentId);

      assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void getByIdShouldReturnForbidden() throws Exception {
      // given
      var commentResponse = CommentTestData.builder().build().buildCommentResponse();
      var commentId = commentResponse.id();

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, commentId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      assertThatCode(() -> commentService.getById(commentId)).doesNotThrowAnyException();
    }
  }

  @Nested
  class Update {

    @ParameterizedTest
    @CsvSource({
      "1, 'Updated comment 1', '2024-09-30T12:00:00'",
      "2, 'Updated comment 2', '2024-09-30T12:00:00'",
      "3, 'Updated comment 3', '2024-09-30T12:00:00'"
    })
    @WithMockUser(authorities = {"comments:write"})
    void updateShouldReturnUpdatedCommentResponse(
        Long commentId, String updatedCommentText, String createdAt) throws Exception {
      // given
      var commentRequest =
          CommentTestData.builder()
              .withCommentText(updatedCommentText)
              .withCreatedAt(LocalDateTime.parse(createdAt))
              .build()
              .buildCommentRequest();

      var expectedResponses =
          CommentTestData.builder()
              .withId(commentId)
              .withCommentText(updatedCommentText)
              .build()
              .buildCommentResponse();

      var requestBuilder =
          put(URL_WITH_PARAMETER_ID, commentId)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  String.format(
                      """
                            {
                                "task": 1,
                                "usersProfile": 1,
                                "commentText": "%s",
                                "createdAt": "%s"
                            }
                            """,
                      updatedCommentText, createdAt));

      // when
      mockMvc
          .perform(requestBuilder)
          // then
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      String.format(
                          """
                                    {
                                        "task": 1,
                                        "usersProfile": 1,
                                        "commentText": "%s",
                                        "createdAt": "%s"
                                    }
                                    """,
                          updatedCommentText, createdAt)));

      assertThatCode(() -> commentService.update(commentId, commentRequest))
          .doesNotThrowAnyException();

      var actualResponse = commentService.update(commentId, commentRequest);

      assertThat(actualResponse).isEqualTo(expectedResponses);
    }

    @Test
    void updateShouldReturnForbidden() throws Exception {
      // given
      var commentId = 1L;

      // when
      mockMvc
          .perform(put(URL_WITH_PARAMETER_ID, commentId))
          // then
          .andExpect(status().isForbidden());
    }
  }

  @Nested
  class Delete {

    @Test
    @WithMockUser(authorities = {"comments:delete"})
    void deleteShouldReturnNoContent() throws Exception {
      // given
      var commentId = 1L;

      // when
      mockMvc
          .perform(delete(URL_WITH_PARAMETER_ID, commentId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isNoContent());

      assertThatCode(() -> commentService.delete(commentId)).doesNotThrowAnyException();
    }

    @Test
    void deleteShouldReturnForbidden() throws Exception {
      // given
      var commentId = 1L;

      // when
      mockMvc
          .perform(delete(URL_WITH_PARAMETER_ID, commentId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());
    }
  }
}
