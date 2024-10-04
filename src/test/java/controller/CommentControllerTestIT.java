package controller;

import config.TestContainersConfiguration;
import data.CommentTestData;
import model.dto.response.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.api.CommentService;
import util.IntegrationTest;
import util.PostgresqlTestContainer;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestContainersConfiguration.class)
class CommentControllerTestIT extends PostgresqlTestContainer {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private static final String URL = "/api/v0/comments";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(commentController)
                .build();
    }

    @Test
    void createShouldReturnCommentResponse() throws Exception {
        // given
        var commentRequest = CommentTestData.builder().build().buildCommentRequest();
        var expectedResponse = CommentTestData.builder().build().buildCommentResponse();

        doReturn(expectedResponse).when(commentService).create(commentRequest);

        var requestBuilder = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "task": 1,
                            "usersProfile": 1,
                            "commentText": "commentText",
                            "createdAt": "2024-09-30T12:00:00"
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
                                         "task": 1,
                                         "usersProfile": 1,
                                         "commentText": "commentText",
                                         "createdAt": "2024-09-30T12:00:00"
                                     }
                                """)
                );
    }

    @Test
    void getAllShouldReturnListOfCommentResponses() throws Exception {
        // given
        var expectedResponses = List.of(
                CommentTestData.builder().build().buildCommentResponse(),
                CommentTestData.builder().withId(2L).build().buildCommentResponse()
        );

        doReturn(expectedResponses).when(commentService).getAll();

        // when
        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                  {
                                    "id": 1,
                                    "task": 1,
                                    "usersProfile": 1,
                                    "commentText": "commentText",
                                    "createdAt": "2024-09-30T12:00:00"
                                  },
                                  {
                                    "id": 2,
                                    "task": 1,
                                    "usersProfile": 1,
                                    "commentText": "commentText",
                                    "createdAt": "2024-09-30T12:00:00"
                                  }
                                ]
                                """)
                );
    }

    @Test
    void getByIdShouldReturnCommentResponse() throws Exception {
        // given
        CommentResponse commentResponse = CommentTestData.builder().build().buildCommentResponse();
        Long commentId = commentResponse.id();

        doReturn(commentResponse).when(commentService).getById(commentId);

        // when
        mockMvc.perform(get(URL_WITH_PARAMETER_ID, commentId)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                   "task": 1,
                                   "usersProfile": 1,
                                   "commentText": "commentText",
                                   "createdAt": "2024-09-30T12:00:00"
                                }
                                """)
                );
    }

    @Test
    void updateShouldReturnUpdatedCommentResponse() throws Exception {
        // given
        Long commentId = 1L;
        var commentRequest = CommentTestData.builder()
                .withCommentText("Updated comment")
                .build().buildCommentRequest();

        var updatedResponse = CommentTestData.builder()
                .withId(commentId)
                .withCommentText("Updated comment")
                .build().buildCommentResponse();

        doReturn(updatedResponse).when(commentService).update(commentId, commentRequest);

        var requestBuilder = put(URL_WITH_PARAMETER_ID, commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "task": 1,
                            "usersProfile": 1,
                            "commentText": "Updated comment",
                            "createdAt": "2024-09-30T12:00:00"
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
                                     "task": 1,
                                     "usersProfile": 1,
                                     "commentText": "Updated comment",
                                     "createdAt": "2024-09-30T12:00:00"
                                }
                                """)
                );
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        // given
        var commentRequest = CommentTestData.builder().build().buildCommentRequest();

        // when
        mockMvc.perform(delete(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                   "task": 1,
                                   "usersProfile": 1,
                                   "commentText": "commentText",
                                   "createdAt": "2024-09-30T12:00:00"
                                }
                                """))
                // then
                .andExpect(status().isNoContent());

        verify(commentService).delete(commentRequest);
    }
}
