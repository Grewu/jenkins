package controller;

import config.TestContainersConfiguration;
import data.ProjectTestData;
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
import service.api.ProjectService;
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
class ProjectControllerTestIT extends PostgresqlTestContainer {

    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private static final String URL = "/api/v0/projects";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(projectController)
                .build();
    }

    @Test
    void createShouldReturnProjectResponse() throws Exception {
        // given
        var projectRequest = ProjectTestData.builder().build().buildProjectRequest();
        var expectedResponse = ProjectTestData.builder().build().buildProjectResponse();

        doReturn(expectedResponse).when(projectService).create(projectRequest);

        var requestBuilder = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "name",
                            "projectCode": "projectCode",
                            "description": "description",
                            "startDate": "2024-09-30T12:00:00",
                            "endDate": "2024-09-30T12:00:00",
                            "owner": 1
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
                                    "name": "name",
                                    "projectCode": "projectCode",
                                    "description": "description",
                                    "startDate": "2024-09-30T12:00:00",
                                    "endDate": "2024-09-30T12:00:00",
                                    "owner": 1
                                }
                                """)
                );
    }

    @Test
    void getAllShouldReturnListOfProjectResponses() throws Exception {
        // given
        var expectedResponses = List.of(
                ProjectTestData.builder().build().buildProjectResponse(),
                ProjectTestData.builder().withId(2L).build().buildProjectResponse()
        );

        doReturn(expectedResponses).when(projectService).getAll();

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
                                    "name": "name",
                                    "projectCode": "projectCode",
                                    "description": "description",
                                    "startDate": "2024-09-30T12:00:00",
                                    "endDate": "2024-09-30T12:00:00",
                                    "owner": 1
                                  },
                                  {
                                    "id": 2,
                                    "name": "name",
                                    "projectCode": "projectCode",
                                    "description": "description",
                                    "startDate": "2024-09-30T12:00:00",
                                    "endDate": "2024-09-30T12:00:00",
                                    "owner": 1
                                  }
                                ]
                                """)
                );
    }

    @Test
    void getByIdShouldReturnProjectResponse() throws Exception {
        // given
        var projectResponse = ProjectTestData.builder().build().buildProjectResponse();
        var projectId = projectResponse.id();

        doReturn(projectResponse).when(projectService).getById(projectId);

        // when
        mockMvc.perform(get(URL_WITH_PARAMETER_ID, projectId)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "name": "name",
                                    "projectCode": "projectCode",
                                    "description": "description",
                                    "startDate": "2024-09-30T12:00:00",
                                    "endDate": "2024-09-30T12:00:00",
                                    "owner": 1
                                }
                                """)
                );
    }

    @Test
    void updateShouldReturnUpdatedProjectResponse() throws Exception {
        // given
        var projectRequest = ProjectTestData.builder()
                .withName("Updated Project")
                .build().buildProjectRequest();

        var updatedResponse = ProjectTestData.builder()
                .withName("Updated Project")
                .build().buildProjectResponse();

        doReturn(updatedResponse).when(projectService).update(1L, projectRequest);

        var requestBuilder = put(URL_WITH_PARAMETER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Updated Project",
                            "projectCode": "projectCode",
                            "description": "description",
                            "startDate": "2024-09-30T12:00:00",
                            "endDate": "2024-09-30T12:00:00",
                            "owner": 1
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
                                    "name": "Updated Project",
                                    "projectCode": "projectCode",
                                    "description": "description",
                                    "startDate": "2024-09-30T12:00:00",
                                    "endDate": "2024-09-30T12:00:00",
                                    "owner": 1
                                }
                                """)
                );
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        // given
        var projectRequest = ProjectTestData.builder().build().buildProjectRequest();

        // when
        mockMvc.perform(delete(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "name",
                                    "projectCode": "projectCode",
                                    "description": "description",
                                    "startDate": "2024-09-30T12:00:00",
                                    "endDate": "2024-09-30T12:00:00",
                                    "owner": 1
                                }
                                """))
                // then
                .andExpect(status().isNoContent());

        verify(projectService).delete(projectRequest);
    }
}