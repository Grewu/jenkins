package controller;

import config.TestContainersConfiguration;
import data.TaskTestData;
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
import service.api.TaskService;
import util.IntegrationTest;
import util.PostgresqlTestContainer;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@WebAppConfiguration
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestContainersConfiguration.class)
class TaskControllerTestIT extends PostgresqlTestContainer {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private static final String URL = "/api/v0/tasks";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(taskController)
                .build();
    }

    @Test
    void createShouldReturnTaskResponse() throws Exception {
        // given
        var taskRequest = TaskTestData.builder().build().buildTaskRequest();
        var expectedResponse = TaskTestData.builder().build().buildTaskResponse();

        doReturn(expectedResponse).when(taskService).create(taskRequest);

        var requestBuilder = post(URL)
                .contentType(MediaType.APPLICATION_JSON).content("""
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
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                    {
                                        "name": "name",
                                        "project": 1,
                                        "assignedTo": 1,
                                        "createdBy": 1,
                                        "dueDate": "2024-09-30T12:00:00",
                                        "status": "IN_PROGRESS",
                                        "priority": "MEDIUM"
                                    }
                                """)
                );
    }

    @Test
    void getAllShouldReturnListOfTaskResponses() throws Exception {
        // given
        var expectedResponses = List.of(
                TaskTestData.builder().build().buildTaskResponse(),
                TaskTestData.builder().withId(2L).build().buildTaskResponse()
        );

        doReturn(expectedResponses).when(taskService).getAll();

        // when
        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                  {     "id": 1,
                                        "name": "name",
                                        "project": 1,
                                        "assignedTo": 1,
                                        "createdBy": 1,
                                        "dueDate": "2024-09-30T12:00:00",
                                        "status": "IN_PROGRESS",
                                        "priority": "MEDIUM"
                                  },
                                  {
                                    "id": 2,
                                    "name": "name",
                                    "project": 1,
                                    "assignedTo": 1,
                                    "createdBy": 1,
                                    "dueDate": "2024-09-30T12:00:00",
                                    "status": "IN_PROGRESS",
                                    "priority": "MEDIUM"
                                  }
                                ]
                                """)
                );
    }

    @Test
    void getByIdShouldReturnTaskResponse() throws Exception {
        // given
        var taskResponse = TaskTestData.builder().build().buildTaskResponse();
        var taskId = taskResponse.id();

        doReturn(taskResponse).when(taskService).getById(taskId);

        // when
        mockMvc.perform(get(URL_WITH_PARAMETER_ID, taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
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
                                """)
                );
    }

    @Test
    void updateShouldReturnUpdatedTaskResponse() throws Exception {
        // given
        var taskRequest = TaskTestData.builder()
                .withName("Updated Task")
                .build().buildTaskRequest();

        var updatedResponse = TaskTestData.builder()
                .withName("Updated Task")
                .build().buildTaskResponse();

        doReturn(updatedResponse).when(taskService).update(1L, taskRequest);

        var requestBuilder = put(URL_WITH_PARAMETER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
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
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
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
                                """)
                );
    }

    @Test
    void deleteTaskShouldReturnNoContent() throws Exception {
        // given
        var taskRequest = TaskTestData.builder().build().buildTaskRequest();

        // when
        mockMvc.perform(delete(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "name",
                                  "project": 1,
                                  "assignedTo": 1,
                                  "createdBy": 1,
                                  "dueDate": "2024-09-30T12:00:00",
                                  "status": "IN_PROGRESS",
                                  "priority": "MEDIUM"
                                }
                                """))
                // then
                .andExpect(status().isNoContent());

        verify(taskService).delete(taskRequest);
    }

}