package ru.senla.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import ru.senla.service.api.CommentService;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {
  private static final String URL = "/api/v0/comments";
  private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private CommentService commentService;

  @Nested
  class Create {
    @Test
    @WithMockUser(authorities = {"comments:write"})
    void createShouldReturnCommentResponse() throws Exception {
      // given
      var commentRequest = CommentTestData.builder().build().buildCommentRequest();
      var expectedResponse = CommentTestData.builder().build().buildCommentResponse();

      doReturn(expectedResponse).when(commentService).create(commentRequest);

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
          .perform(requestBuilder)
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

      verify(commentService).create(any());
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
          .perform(requestBuilder)
          // then
          .andExpect(status().isForbidden());

      verify(commentService, never()).create(any());
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
              CommentTestData.builder().withId(2L).build().buildCommentResponse());
      var expectedPage = new PageImpl<>(expectedResponses, pageable, 2);

      when(commentService.getAll(any(Pageable.class))).thenReturn(expectedPage);

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
      // when
      mockMvc
          .perform(get(URL).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(commentService, never()).getAll(any());
    }
  }

  @Nested
  class GetByID {
    @Test
    @WithMockUser(authorities = {"comments:read"})
    void getByIdShouldReturnCommentResponse() throws Exception {
      // given
      var commentResponse = CommentTestData.builder().build().buildCommentResponse();
      var commentId = commentResponse.id();

      doReturn(commentResponse).when(commentService).getById(commentId);

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
      verify(commentService).getById(any());
    }

    @Test
    void getByIdShouldReturnForbidden() throws Exception {
      // given
      var commentResponse = CommentTestData.builder().build().buildCommentResponse();
      var commentId = commentResponse.id();

      doReturn(commentResponse).when(commentService).getById(commentId);

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, commentId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(commentService, never()).getById(any());
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

      var updatedResponse =
          CommentTestData.builder()
              .withId(commentId)
              .withCommentText(updatedCommentText)
              .build()
              .buildCommentResponse();

      doReturn(updatedResponse).when(commentService).update(commentId, commentRequest);

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

      verify(commentService).update(any(), any());
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

      verify(commentService, never()).update(any(), any());
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

      verify(commentService).delete(commentId);
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

      verify(commentService, never()).delete(commentId);
    }
  }
}
