package ru.senla.integration;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.senla.data.ProjectTestData;
import ru.senla.data.TaskTestData;
import ru.senla.service.api.ProjectService;
import ru.senla.util.DateTimeFormatterUtil;
import ru.senla.util.IntegrationTest;
import ru.senla.util.PostgresqlTestContainer;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@IntegrationTest
@AutoConfigureMockMvc
class ProjectControllerTestIT extends PostgresqlTestContainer {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
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
            mockMvc.perform(requestBuilder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
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

            assertThatCode(() -> projectService.create(projectRequest))
                    .doesNotThrowAnyException();
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
            mockMvc.perform(requestBuilder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                    // then
                    .andExpect(status().isForbidden());
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
                    TaskTestData.builder().withId(2L).build().buildTaskResponse(),
                    TaskTestData.builder().withId(3L).build().buildTaskResponse(),
                    TaskTestData.builder().withId(4L).build().buildTaskResponse()
            );

            // when
            mockMvc.perform(get(URL_WITH_PROJECT_ID_TASKS, projectId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content.size()").value(expectedResponses.size()))
                    .andExpect(jsonPath("$.content[0].id").value(expectedResponses.get(0).id()))
                    .andExpect(jsonPath("$.content[0].name").value(expectedResponses.get(0).name()))
                    .andExpect(jsonPath("$.content[0].project").value(expectedResponses.get(0).project()))
                    .andExpect(jsonPath("$.content[0].assignedTo").value(expectedResponses.get(0).assignedTo()))
                    .andExpect(jsonPath("$.content[0].createdBy").value(expectedResponses.get(0).createdBy()))
                    .andExpect(jsonPath("$.content[0].dueDate").value(expectedResponses.get(0).dueDate()
                            .format(DateTimeFormatterUtil.DATE_TIME_FORMATTER)))
                    .andExpect(jsonPath("$.content[0].status").value(expectedResponses.get(0).status().toString()))
                    .andExpect(jsonPath("$.content[0].priority").value(expectedResponses.get(0).priority().toString()))
                    .andExpect(jsonPath("$.content[1].id").value(expectedResponses.get(1).id()))
                    .andExpect(jsonPath("$.content[1].name").value(expectedResponses.get(1).name()))
                    .andExpect(jsonPath("$.content[1].project").value(expectedResponses.get(1).project()))
                    .andExpect(jsonPath("$.content[1].assignedTo").value(expectedResponses.get(1).assignedTo()))
                    .andExpect(jsonPath("$.content[1].createdBy").value(expectedResponses.get(1).createdBy()))
                    .andExpect(jsonPath("$.content[1].dueDate").value(expectedResponses.get(1).dueDate()
                            .format(DateTimeFormatterUtil.DATE_TIME_FORMATTER)))
                    .andExpect(jsonPath("$.content[1].status").value(expectedResponses.get(1).status().toString()))
                    .andExpect(jsonPath("$.content[1].priority").value(expectedResponses.get(1).priority().toString()));

            assertThatCode(() -> projectService.getAllTaskRelatedToProjectByProjectId(projectId, pageable))
                    .doesNotThrowAnyException();


            var actualResponse = projectService
                    .getAllTaskRelatedToProjectByProjectId(projectId, pageable).stream().toList();

            IntStream.range(0, actualResponse.size())
                    .forEach((i) ->
                            assertThat(actualResponse.get(i)).isEqualTo(expectedResponses.get(i))
                    );
        }

        @Test
        @WithMockUser(authorities = {"project:read"})
        void getAllShouldReturnListOfProjectResponses() throws Exception {
            // given
            var pageable = Pageable.ofSize(2);

            var expectedResponses = List.of(
                    ProjectTestData.builder().build().buildProjectResponse(),
                    ProjectTestData.builder().withId(2L).build().buildProjectResponse(),
                    ProjectTestData.builder().withId(3L).build().buildProjectResponse()
            );

            // when
            mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content.size()").value(expectedResponses.size()))
                    .andExpect(jsonPath("$.content[0].id").value(expectedResponses.get(0).id()))
                    .andExpect(jsonPath("$.content[0].name").value(expectedResponses.get(0).name()))
                    .andExpect(jsonPath("$.content[0].projectCode").value(expectedResponses.get(0).projectCode()))
                    .andExpect(jsonPath("$.content[0].description").value(expectedResponses.get(0).description()))
                    .andExpect(jsonPath("$.content[0].startDate").value(expectedResponses.get(0).startDate()
                            .format(DateTimeFormatterUtil.DATE_TIME_FORMATTER)))
                    .andExpect(jsonPath("$.content[0].endDate").value(expectedResponses.get(0).endDate()
                            .format(DateTimeFormatterUtil.DATE_TIME_FORMATTER)))
                    .andExpect(jsonPath("$.content[0].owner").value(expectedResponses.get(0).owner()))
                    .andExpect(jsonPath("$.content[1].id").value(expectedResponses.get(1).id()))
                    .andExpect(jsonPath("$.content[1].name").value(expectedResponses.get(1).name()))
                    .andExpect(jsonPath("$.content[1].projectCode").value(expectedResponses.get(1).projectCode()))
                    .andExpect(jsonPath("$.content[1].description").value(expectedResponses.get(1).description()))
                    .andExpect(jsonPath("$.content[1].startDate").value(expectedResponses.get(1).startDate()
                            .format(DateTimeFormatterUtil.DATE_TIME_FORMATTER)))
                    .andExpect(jsonPath("$.content[1].endDate").value(expectedResponses.get(1).endDate()
                            .format(DateTimeFormatterUtil.DATE_TIME_FORMATTER)))
                    .andExpect(jsonPath("$.content[1].owner").value(expectedResponses.get(1).owner()));

            assertThatCode(() -> projectService.getAll(pageable))
                    .doesNotThrowAnyException();


            var actualResponse = projectService.getAll(pageable).stream().toList();

            IntStream.range(0, actualResponse.size())
                    .forEach((i) ->
                            assertThat(actualResponse.get(i)).isEqualTo(expectedResponses.get(i))
                    );

        }

        @Test
        void getAllShouldReturnForbidden() throws Exception {
            // when
            mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class GetByID {
        @Test
        @WithMockUser(authorities = {"project:read"})
        void getByIdShouldReturnProjectResponse() throws Exception {
            // given
            var expectedResponse = ProjectTestData.builder().build().buildProjectResponse();
            var projectId = expectedResponse.id();

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
            assertThatCode(() -> projectService.getById(projectId))
                    .doesNotThrowAnyException();

            var actualResponse = projectService.getById(projectId);

            assertThat(actualResponse).isEqualTo(expectedResponse);
        }

        @Test
        void getByIdShouldReturnForbidden() throws Exception {
            // given
            var projectResponse = ProjectTestData.builder().build().buildProjectResponse();
            var projectId = projectResponse.id();

            // when
            mockMvc.perform(get(URL_WITH_PARAMETER_ID, projectId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isForbidden());

            assertThatCode(() -> projectService.getById(projectId))
                    .doesNotThrowAnyException();
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

            var expectedResponses = ProjectTestData.builder()
                    .withId(projectId)
                    .withName("Updated Project")
                    .build().buildProjectResponse();

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

            assertThatCode(() -> projectService.update(projectId, projectRequest))
                    .doesNotThrowAnyException();

            var actualResponse = projectService.update(projectId, projectRequest);

            assertThat(actualResponse).isEqualTo(expectedResponses);
        }

        @Test
        void updateShouldReturnForbidden() throws Exception {
            // given
            var projectId = 1L;

            // when
            mockMvc.perform(put(URL_WITH_PARAMETER_ID, projectId))
                    // then
                    .andExpect(status().isForbidden());

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

            assertThatCode(() -> projectService.delete(projectId))
                    .doesNotThrowAnyException();
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
        }
    }
}
