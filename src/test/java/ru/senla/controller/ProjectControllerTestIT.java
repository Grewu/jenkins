package ru.senla.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.senla.data.ProjectTestData;
import ru.senla.data.TaskTestData;
import ru.senla.service.api.ProjectService;
import ru.senla.util.IntegrationTest;
import ru.senla.util.PostgresqlTestContainer;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@IntegrationTest
@AutoConfigureMockMvc
class ProjectControllerTestIT extends PostgresqlTestContainer {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private static final String URL = "/api/v0/projects";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";
    private static final String URL_WITH_PROJECT_ID_TASKS = URL + "/{projectId}/tasks";

    @Nested
    class Create {
        @Test
        @WithMockUser(authorities = {"project:write"})
        void createShouldReturnProjectResponse() throws Exception {
            // given
            var projectRequest = ProjectTestData.builder().build().buildProjectRequest();
            var expectedResponse = ProjectTestData.builder().build().buildProjectResponse();

            doReturn(expectedResponse)
                    .when(projectService).create(projectRequest);

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
                                                                 "name": "name",
                                                                 "projectCode": "projectCode",
                                                                 "description": "description",
                                                                 "startDate": "2024-09-30T12:00:00",
                                                                 "endDate": "2024-09-30T12:00:00",
                                                                 "owner": 1
                                       }
                                    """)
                    );

            verify(projectService).create(any());
        }

        @Test
        void createShouldReturnForbidden() throws Exception {
            // given
            var requestBuilder = post(URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                             {
                                                                "id": 1,
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
                    .andExpect(status().isForbidden());

            verify(projectService, never()).create(any());
        }
    }

    @Nested
    class GetAll {
        @Test
        @WithMockUser(authorities = {"project:read", "task:read"})
        void getAllShouldReturnTaskResponseRelatedToProject() throws Exception {
            // given
            var pageable = Pageable.ofSize(2);
            var projectId = ProjectTestData.builder().build().buildProject().getId();

            var expectedResponses = List.of(
                    TaskTestData.builder().build().buildTaskResponse(),
                    TaskTestData.builder().withId(2L).build().buildTaskResponse()
            );

            when(projectService.getAllTaskRelatedToProjectByProjectId(anyLong(), any(Pageable.class)))
                    .thenReturn(new PageImpl<>(expectedResponses, pageable, 2));
            // when
            mockMvc.perform(get(URL_WITH_PROJECT_ID_TASKS, projectId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content.size()").value(2))
                    .andExpect(jsonPath("$.content[0].id").value(1))
                    .andExpect(jsonPath("$.content[0].name").value("name"))
                    .andExpect(jsonPath("$.content[0].project").value(1))
                    .andExpect(jsonPath("$.content[0].assignedTo").value(1))
                    .andExpect(jsonPath("$.content[0].createdBy").value(1))
                    .andExpect(jsonPath("$.content[0].dueDate").value("2024-09-30T12:00:00"))
                    .andExpect(jsonPath("$.content[0].status").value("IN_PROGRESS"))
                    .andExpect(jsonPath("$.content[0].priority").value("MEDIUM"))
                    .andExpect(jsonPath("$.content[1].id").value(2))
                    .andExpect(jsonPath("$.content[1].name").value("name"))
                    .andExpect(jsonPath("$.content[1].project").value(1))
                    .andExpect(jsonPath("$.content[1].assignedTo").value(1))
                    .andExpect(jsonPath("$.content[1].createdBy").value(1))
                    .andExpect(jsonPath("$.content[1].dueDate").value("2024-09-30T12:00:00"))
                    .andExpect(jsonPath("$.content[1].status").value("IN_PROGRESS"))
                    .andExpect(jsonPath("$.content[1].priority").value("MEDIUM"));
        }

        @Test
        @WithMockUser(authorities = {"project:read"})
        void getAllShouldReturnListOfProjectResponses() throws Exception {
            // given
            var pageable = Pageable.ofSize(2);

            var expectedResponses = List.of(
                    ProjectTestData.builder().build().buildProjectResponse(),
                    ProjectTestData.builder().withId(2L).build().buildProjectResponse()
            );

            when(projectService.getAll(any(Pageable.class)))
                    .thenReturn(new PageImpl<>(expectedResponses, pageable, 2));

            // when
            mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content.size()").value(2))
                    .andExpect(jsonPath("$.content[0].id").value(1))
                    .andExpect(jsonPath("$.content[0].name").value("name"))
                    .andExpect(jsonPath("$.content[0].projectCode").value("projectCode"))
                    .andExpect(jsonPath("$.content[0].description").value("description"))
                    .andExpect(jsonPath("$.content[0].startDate").value("2024-09-30T12:00:00"))
                    .andExpect(jsonPath("$.content[0].endDate").value("2024-09-30T12:00:00"))
                    .andExpect(jsonPath("$.content[0].owner").value(1))
                    .andExpect(jsonPath("$.content[1].id").value(2))
                    .andExpect(jsonPath("$.content[1].name").value("name"))
                    .andExpect(jsonPath("$.content[1].projectCode").value("projectCode"))
                    .andExpect(jsonPath("$.content[1].description").value("description"))
                    .andExpect(jsonPath("$.content[1].startDate").value("2024-09-30T12:00:00"))
                    .andExpect(jsonPath("$.content[1].endDate").value("2024-09-30T12:00:00"))
                    .andExpect(jsonPath("$.content[1].owner").value(1));

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

            verify(projectService, never()).getAll(pageable);
        }
    }

    @Nested
    class GetByID {
        @Test
        @WithMockUser(authorities = {"project:read"})
        void getByIdShouldReturnProjectResponse() throws Exception {
            // given
            var projectResponse = ProjectTestData.builder().build().buildProjectResponse();
            var projectId = projectResponse.id();

            doReturn(projectResponse)
                    .when(projectService).getById(projectId);

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
            verify(projectService).getById(any());
        }

        @Test
        void getByIdShouldReturnForbidden() throws Exception {
            // given
            var projectResponse = ProjectTestData.builder().build().buildProjectResponse();
            var projectId = projectResponse.id();

            doReturn(projectResponse)
                    .when(projectService).getById(projectId);

            // when
            mockMvc.perform(get(URL_WITH_PARAMETER_ID, projectId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isForbidden());

            verify(projectService, never()).getById(any());
        }
    }

    @Nested
    class Update {
        @Test
        @WithMockUser(authorities = {"project:write"})
        void updateShouldReturnUpdatedProjectResponse() throws Exception {
            // given
            var projectId = 1L;
            var projectRequest = ProjectTestData.builder()
                    .withName("Updated Project")
                    .build().buildProjectRequest();

            var updatedResponse = ProjectTestData.builder()
                    .withId(projectId)
                    .withName("Updated Project")
                    .build().buildProjectResponse();

            doReturn(updatedResponse).
                    when(projectService).update(projectId, projectRequest);

            var requestBuilder = put(URL_WITH_PARAMETER_ID, projectId)
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

            verify(projectService).update(any(), any());
        }

        @Test
        void updateShouldReturnForbidden() throws Exception {
            // given
            var projectId = 1L;

            // when
            mockMvc.perform(put(URL_WITH_PARAMETER_ID, projectId)
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
                                             """))
                    // then
                    .andExpect(status().isForbidden());

            verify(projectService, never()).update(any(), any());
        }
    }

    @Nested
    class Delete {
        @Test
        @WithMockUser(authorities = {"project:delete"})
        void deleteShouldReturnNoContent() throws Exception {
            // given
            var projectId = 1L;

            // when
            mockMvc.perform(delete(URL_WITH_PARAMETER_ID, projectId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isNoContent());

            verify(projectService).delete(projectId);
        }

        @Test
        void deleteShouldReturnForbidden() throws Exception {
            // given
            var projectId = 1L;

            // when
            mockMvc.perform(delete(URL_WITH_PARAMETER_ID, projectId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isForbidden());

            verify(projectService, never()).delete(projectId);
        }
    }
}
