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
import ru.senla.data.TaskHistoryTestData;
import ru.senla.data.TaskTestData;
import ru.senla.model.filter.TaskFilter;
import ru.senla.service.api.TaskService;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private TaskService taskService;

  private static final String URL = "/api/v0/tasks";
  private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";
  private static final String URL_WITH_FILTER = URL + "/filter";
  private static final String URL_WITH_TASK_ID = URL + "/{taskId}/history";

  @Nested
  class Create {
    @Test
    @WithMockUser(authorities = {"task:write"})
    void createShouldReturnTaskResponse() throws Exception {
      // given
      var taskRequest = TaskTestData.builder().build().buildTaskRequest();
      var expectedResponse = TaskTestData.builder().build().buildTaskResponse();

      doReturn(expectedResponse).when(taskService).create(taskRequest);

      var requestBuilder =
          post(URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                                            {
                                               "name": "name",
                                               "project": 1,
                                               "assignedTo": 1,
                                               "createdBy": 1,
                                               "dueDate": "2024-09-30T12:00:00",
                                               "status": "IN_PROGRESS",
                                               "priority": "MEDIUM"
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
                                                      "name": "name",
                                                      "project": 1,
                                                      "assignedTo": 1,
                                                      "createdBy": 1,
                                                      "dueDate": "2024-09-30T12:00:00",
                                                      "status": "IN_PROGRESS",
                                                      "priority": "MEDIUM"
                                                    }
                                                    """));

      verify(taskService).create(any());
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
                                              "name": "name",
                                              "project": 1,
                                              "assignedTo": 1,
                                              "createdBy": 1,
                                              "dueDate": "2024-09-30T12:00:00",
                                              "status": "IN_PROGRESS",
                                              "priority": "MEDIUM"
                                            }
                                            """);

      // when
      mockMvc
          .perform(requestBuilder)
          // then
          .andExpect(status().isForbidden());

      verify(taskService, never()).create(any());
    }
  }

  @Nested
  class GetAll {
    @Test
    @WithMockUser(authorities = {"task:read", "task_history:read"})
    void getAllTaskHistoryByTaskIdShouldReturnPageOfTaskHistoryResponse() throws Exception {
      var pageable = Pageable.ofSize(2);
      var taskId = TaskTestData.builder().build().buildTask().getId();
      var expectedResponses =
          List.of(
              TaskHistoryTestData.builder().build().buildTaskHistoryResponse(),
              TaskHistoryTestData.builder().withId(2L).build().buildTaskHistoryResponse());

      when(taskService.getAllTaskHistory(anyLong(), any(Pageable.class)))
          .thenReturn(new PageImpl<>(expectedResponses, pageable, expectedResponses.size()));

      mockMvc
          .perform(get(URL_WITH_TASK_ID, taskId).contentType(MediaType.APPLICATION_JSON))
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      objectMapper.writeValueAsString(
                          new PageImpl<>(expectedResponses, pageable, expectedResponses.size()))));
    }

    @Test
    @WithMockUser(authorities = {"task:read"})
    void getAllByFilterShouldReturnPageOfTaskResponses() throws Exception {
      var pageable = Pageable.ofSize(2);
      var expectedResponses =
          List.of(
              TaskTestData.builder().build().buildTaskResponse(),
              TaskTestData.builder().withId(2L).build().buildTaskResponse());

      var expectedPage = new PageImpl<>(expectedResponses, pageable, expectedResponses.size());

      when(taskService.getAllByFilter(any(TaskFilter.class), any(Pageable.class)))
          .thenReturn(expectedPage);

      mockMvc
          .perform(get(URL_WITH_FILTER).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json(objectMapper.writeValueAsString(expectedPage)));
    }

    @Test
    void getAllByFilterShouldReturnForbidden() throws Exception {
      // when
      mockMvc
          .perform(get(URL_WITH_FILTER).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(taskService, never()).getAllByFilter(any(TaskFilter.class), any(Pageable.class));
    }

    @Test
    @WithMockUser(authorities = {"task:read"})
    void getAllShouldReturnPageOfTaskResponses() throws Exception {
      // given
      var pageable = Pageable.ofSize(2);
      var expectedResponses =
          List.of(
              TaskTestData.builder().build().buildTaskResponse(),
              TaskTestData.builder().withId(2L).build().buildTaskResponse());
      var expectedPage = new PageImpl<>(expectedResponses, pageable, expectedResponses.size());

      when(taskService.getAll(any(Pageable.class))).thenReturn(expectedPage);

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

      verify(taskService, never()).getAll(pageable);
    }
  }

  @Nested
  class GetByID {
    @Test
    @WithMockUser(authorities = {"task:read"})
    void getByIdShouldReturnTaskResponse() throws Exception {
      // given
      var taskResponse = TaskTestData.builder().build().buildTaskResponse();
      var taskId = taskResponse.id();

      doReturn(taskResponse).when(taskService).getById(taskId);

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, taskId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      """
                                                    {
                                                       "id": 1,
                                                       "name": "name",
                                                       "project": 1,
                                                       "assignedTo": 1,
                                                       "createdBy": 1,
                                                       "dueDate": "2024-09-30T12:00:00",
                                                       "status": "IN_PROGRESS",
                                                       "priority": "MEDIUM"
                                                    }
                                                    """));
      verify(taskService).getById(any());
    }

    @Test
    void getByIdShouldReturnForbidden() throws Exception {
      // given
      var taskResponse = TaskTestData.builder().build().buildTaskResponse();
      var taskId = taskResponse.id();

      doReturn(taskResponse).when(taskService).getById(taskId);

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, taskId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(taskService, never()).getById(any());
    }
  }

  @Nested
  class Update {
    @Test
    @WithMockUser(authorities = {"task:write"})
    void updateShouldReturnUpdatedTaskResponse() throws Exception {
      // given
      var taskId = 1L;
      var taskRequest = TaskTestData.builder().withName("Updated Task").build().buildTaskRequest();

      var updatedResponse =
          TaskTestData.builder()
              .withId(taskId)
              .withName("Updated Task")
              .build()
              .buildTaskResponse();

      doReturn(updatedResponse).when(taskService).update(taskId, taskRequest);

      var requestBuilder =
          put(URL_WITH_PARAMETER_ID, taskId)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                                            {
                                              "name": "Updated Task",
                                              "project": 1,
                                              "assignedTo": 1,
                                              "createdBy": 1,
                                              "dueDate": "2024-09-30T12:00:00",
                                              "status": "IN_PROGRESS",
                                              "priority": "MEDIUM"
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
                                                       "name": "Updated Task",
                                                       "project": 1,
                                                       "assignedTo": 1,
                                                       "createdBy": 1,
                                                       "dueDate": "2024-09-30T12:00:00",
                                                       "status": "IN_PROGRESS",
                                                       "priority": "MEDIUM"
                                                    }
                                                    """));

      verify(taskService).update(any(), any());
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

      verify(taskService, never()).update(any(), any());
    }
  }

  @Nested
  class Delete {
    @Test
    @WithMockUser(authorities = {"task:delete"})
    void deleteShouldReturnNoContent() throws Exception {
      // given
      var taskId = 1L;

      // when
      mockMvc
          .perform(delete(URL_WITH_PARAMETER_ID, taskId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isNoContent());

      verify(taskService).delete(taskId);
    }

    @Test
    void deleteShouldReturnForbidden() throws Exception {
      // given
      var taskId = 1L;

      // when
      mockMvc
          .perform(delete(URL_WITH_PARAMETER_ID, taskId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(taskService, never()).delete(taskId);
    }
  }
}
