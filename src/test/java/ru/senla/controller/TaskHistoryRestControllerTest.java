package ru.senla.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import ru.senla.data.TaskHistoryTestData;
import ru.senla.data.TaskTestData;
import ru.senla.service.api.TaskHistoryService;

@SpringBootTest
@AutoConfigureMockMvc
class TaskHistoryRestControllerTest {
  private static final String URL = "/api/v0/task_history";
  private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private TaskHistoryService taskHistoryService;

  @Nested
  class GetAll {
    @Test
    @WithMockUser(authorities = {"task_history:read"})
    void getAllShouldReturnListOfTaskHistoryResponses() throws Exception {
      // given
      var pageable = Pageable.ofSize(2);

      var expectedResponses =
          List.of(
              TaskHistoryTestData.builder().build().buildTaskHistoryResponse(),
              TaskHistoryTestData.builder().withId(2L).build().buildTaskHistoryResponse());

      var expectedPage = new PageImpl<>(expectedResponses, pageable, expectedResponses.size());

      when(taskHistoryService.getAll(any(Pageable.class))).thenReturn(expectedPage);

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

      verify(taskHistoryService, never()).getAll(pageable);
    }
  }

  @Nested
  class GetByID {
    @Test
    @WithMockUser(authorities = {"task_history:read"})
    void getByIdShouldReturnTaskResponse() throws Exception {
      // given
      var taskHistoryResponse = TaskHistoryTestData.builder().build().buildTaskHistoryResponse();
      var taskHistoryId = taskHistoryResponse.id();

      doReturn(taskHistoryResponse).when(taskHistoryService).getById(taskHistoryId);

      // when
      mockMvc
          .perform(
              get(URL_WITH_PARAMETER_ID, taskHistoryId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      """
                                    {
                                        "id": 1,
                                        "task": 1,
                                        "name": "name",
                                        "project": 1,
                                        "assignedTo": 1,
                                        "createdBy": 1,
                                        "dueDate": "2024-09-30T12:00:00",
                                        "status": "IN_PROGRESS",
                                        "priority": "MEDIUM",
                                        "changedBy": 1,
                                        "changedDate": "2024-09-30T12:00:00",
                                        "changedDescription": "changedDescription"
                                    }
                                    """));
      verify(taskHistoryService).getById(any());
    }

    @Test
    void getByIdShouldReturnForbidden() throws Exception {
      // given
      var taskResponse = TaskTestData.builder().build().buildTaskResponse();
      var taskId = taskResponse.id();

      doReturn(taskResponse).when(taskHistoryService).getById(taskId);

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, taskId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(taskHistoryService, never()).getById(any());
    }
  }

  @Nested
  class Update {
    @Test
    @WithMockUser(authorities = {"task_history:write"})
    void updateShouldReturnUpdatedTaskResponse() throws Exception {
      // given
      var taskId = 1L;
      var taskHistoryRequest =
          TaskHistoryTestData.builder()
              .withName("Update TaskHistory")
              .build()
              .buildTaskHistoryRequest();

      var updateTaskHistory =
          TaskHistoryTestData.builder()
              .withName("Update TaskHistory")
              .build()
              .buildTaskHistoryResponse();

      doReturn(updateTaskHistory).when(taskHistoryService).update(taskId, taskHistoryRequest);

      var requestBuilder =
          put(URL_WITH_PARAMETER_ID, taskId)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                             {
                                 "id": 1,
                                 "task": 1,
                                 "name": "Update TaskHistory",
                                 "project": 1,
                                 "assignedTo": 1,
                                 "createdBy": 1,
                                 "dueDate": "2024-09-30T12:00:00",
                                 "status": "IN_PROGRESS",
                                 "priority": "MEDIUM",
                                 "changedBy": 1,
                                 "changedDate": "2024-09-30T12:00:00",
                                 "changedDescription": "changedDescription"
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
                                        "task": 1,
                                        "name": "Update TaskHistory",
                                        "project": 1,
                                        "assignedTo": 1,
                                        "createdBy": 1,
                                        "dueDate": "2024-09-30T12:00:00",
                                        "status": "IN_PROGRESS",
                                        "priority": "MEDIUM",
                                        "changedBy": 1,
                                        "changedDate": "2024-09-30T12:00:00",
                                        "changedDescription": "changedDescription"
                                    }
                                    """));

      verify(taskHistoryService).update(any(), any());
    }

    @Test
    void updateShouldReturnForbidden() throws Exception {
      // given
      var taskId = 1L;

      // when
      mockMvc
          .perform(put(URL_WITH_PARAMETER_ID, taskId))
          // then
          .andExpect(status().isForbidden());

      verify(taskHistoryService, never()).update(any(), any());
    }
  }

  @Nested
  class Delete {
    @Test
    @WithMockUser(authorities = {"task_history:delete"})
    void deleteShouldReturnNoContent() throws Exception {
      // given
      var taskId = 1L;

      // when
      mockMvc
          .perform(delete(URL_WITH_PARAMETER_ID, taskId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isNoContent());

      verify(taskHistoryService).delete(taskId);
    }

    @Test
    @WithMockUser(authorities = {"task_history:read"})
    void deleteShouldReturnForbiddenForUser() throws Exception {
      // given
      var taskId = 1L;

      // when
      mockMvc
          .perform(delete(URL_WITH_PARAMETER_ID, taskId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(taskHistoryService, never()).delete(taskId);
    }
  }
}
