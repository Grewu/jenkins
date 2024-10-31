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
import ru.senla.data.ProjectTestData;
import ru.senla.data.TaskTestData;
import ru.senla.model.dto.request.ProjectRequest;
import ru.senla.service.api.ProjectService;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private ProjectService projectService;

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

      when(projectService.create(any(ProjectRequest.class))).thenReturn(expectedResponse);

      var requestBuilder =
          post(URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(projectRequest));

      // when
      mockMvc
          .perform(requestBuilder)
          // then
          .andExpect(status().isCreated())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

      verify(projectService).create(any(ProjectRequest.class));
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
      mockMvc
          .perform(requestBuilder)
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

      var expectedResponses =
          List.of(
              TaskTestData.builder().build().buildTaskResponse(),
              TaskTestData.builder().withId(2L).build().buildTaskResponse());

      var expectedPage = new PageImpl<>(expectedResponses, pageable, expectedResponses.size());

      when(projectService.getAllTaskRelatedToProjectByProjectId(anyLong(), any(Pageable.class)))
          .thenReturn(expectedPage);

      // when
      mockMvc
          .perform(
              get(URL_WITH_PROJECT_ID_TASKS, projectId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json(objectMapper.writeValueAsString(expectedPage)));
    }

    @Test
    @WithMockUser(authorities = {"project:read"})
    void getAllShouldReturnListOfProjectResponses() throws Exception {
      // given
      var pageable = Pageable.ofSize(2);

      var expectedResponses =
          List.of(
              ProjectTestData.builder().build().buildProjectResponse(),
              ProjectTestData.builder().withId(2L).build().buildProjectResponse());

      var expectedPage = new PageImpl<>(expectedResponses, pageable, expectedResponses.size());

      when(projectService.getAll(any(Pageable.class))).thenReturn(expectedPage);

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

      doReturn(projectResponse).when(projectService).getById(projectId);

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, projectId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json(objectMapper.writeValueAsString(projectResponse)));

      verify(projectService).getById(projectId);
    }

    @Test
    void getByIdShouldReturnForbidden() throws Exception {
      // given
      var projectResponse = ProjectTestData.builder().build().buildProjectResponse();
      var projectId = projectResponse.id();

      doReturn(projectResponse).when(projectService).getById(projectId);

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, projectId).contentType(MediaType.APPLICATION_JSON))
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
      var projectRequest =
          ProjectTestData.builder().withName("Updated Project").build().buildProjectRequest();

      var updatedResponse =
          ProjectTestData.builder()
              .withId(projectId)
              .withName("Updated Project")
              .build()
              .buildProjectResponse();

      doReturn(updatedResponse).when(projectService).update(projectId, projectRequest);

      var requestBuilder =
          put(URL_WITH_PARAMETER_ID, projectId)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(projectRequest));

      // when
      mockMvc
          .perform(requestBuilder)
          // then
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json(objectMapper.writeValueAsString(updatedResponse)));

      verify(projectService).update(projectId, projectRequest);
    }

    @Test
    void updateShouldReturnForbidden() throws Exception {
      // given
      var projectId = 1L;

      // when
      mockMvc
          .perform(
              put(URL_WITH_PARAMETER_ID, projectId)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      """
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
      mockMvc
          .perform(delete(URL_WITH_PARAMETER_ID, projectId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isNoContent());

      verify(projectService).delete(projectId);
    }

    @Test
    void deleteShouldReturnForbidden() throws Exception {
      // given
      var projectId = 1L;

      // when
      mockMvc
          .perform(delete(URL_WITH_PARAMETER_ID, projectId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(projectService, never()).delete(projectId);
    }
  }
}
