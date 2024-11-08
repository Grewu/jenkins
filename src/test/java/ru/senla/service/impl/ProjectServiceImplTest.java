package ru.senla.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.senla.data.ProjectTestData;
import ru.senla.data.TaskTestData;
import ru.senla.data.UserProfileTestData;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.ProjectMapper;
import ru.senla.mapper.TaskMapper;
import ru.senla.repository.api.ProjectRepository;
import ru.senla.repository.api.TaskRepository;
import ru.senla.repository.api.UserProfileRepository;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {
  private static final String ERROR_MESSAGE = "Project with ID -1 was not found";
  @Mock private ProjectMapper projectMapper;
  @Mock private TaskMapper taskMapper;
  @Mock private ProjectRepository projectRepository;

  @Mock private UserProfileRepository userProfileRepository;

  @Mock private TaskRepository taskRepository;

  @InjectMocks private ProjectServiceImpl projectService;

  @Nested
  class Create {
    @Test
    void createShouldReturnProjectResponse() {
      // given
      var projectRequest = ProjectTestData.builder().build().buildProjectRequest();
      var project = ProjectTestData.builder().build().buildProject();
      var expected = ProjectTestData.builder().build().buildProjectResponse();

      when(projectMapper.toProject(projectRequest)).thenReturn(project);
      when(projectRepository.save(project)).thenReturn(project);
      when(projectMapper.toProjectResponse(project)).thenReturn(expected);

      // when
      var actual = projectService.create(projectRequest);

      // then
      verify(projectRepository).save(project);
      verify(projectMapper).toProject(projectRequest);
      verify(projectMapper).toProjectResponse(project);
      assertEquals(expected, actual);
    }
  }

  @Nested
  class GetAll {

    @Test
    void getAllTaskRelatedToProjectByProjectId() {
      // given
      var pageable = Pageable.ofSize(2);
      var projectId = ProjectTestData.builder().build().buildProject().getId();
      var tasks = List.of(TaskTestData.builder().build().buildTask());
      var expectedResponses = List.of(TaskTestData.builder().build().buildTaskResponse());

      var taskPage = new PageImpl<>(tasks, pageable, 2);

      doReturn(taskPage).when(taskRepository).findTasksByProjectId(projectId, pageable);

      IntStream.range(0, tasks.size())
          .forEach(
              i ->
                  doReturn(expectedResponses.get(i)).when(taskMapper).toTaskResponse(tasks.get(i)));

      // when
      var actualResponses =
          projectService.getAllTaskRelatedToProjectByProjectId(projectId, pageable).getContent();

      // then
      assertEquals(expectedResponses, actualResponses);
    }

    @Test
    void getAllShouldReturnListOfProjectResponse() {
      // given
      var pageable = Pageable.ofSize(2);
      var projects = List.of(ProjectTestData.builder().build().buildProject());
      var expectedResponses = List.of(ProjectTestData.builder().build().buildProjectResponse());
      var projectPage = new PageImpl<>(projects, pageable, 2);

      doReturn(projectPage).when(projectRepository).findAll(pageable);

      IntStream.range(0, projects.size())
          .forEach(
              i ->
                  doReturn(expectedResponses.get(i))
                      .when(projectMapper)
                      .toProjectResponse(projects.get(i)));

      // when
      var actualResponses = projectService.getAll(pageable).getContent();

      // then
      assertEquals(expectedResponses, actualResponses);
    }
  }

  @Nested
  class GetById {
    @Test
    void getByIdShouldReturnExpectedProjectResponse() {
      // given
      var project = ProjectTestData.builder().build().buildProject();
      var expected = ProjectTestData.builder().build().buildProjectResponse();

      when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
      when(projectMapper.toProjectResponse(project)).thenReturn(expected);

      // when
      var actual = projectService.getById(project.getId());

      // then
      assertEquals(expected, actual);
    }

    @Test
    void getByIdShouldThrowNotFoundException() {
      // given
      var id = -1L;
      // when
      var exception = assertThrows(EntityNotFoundException.class, () -> projectService.getById(id));

      // then
      assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
  }

  @Nested
  class Update {
    @Test
    void updateShouldReturnProjectResponse() {
      // given
      var projectRequest = ProjectTestData.builder().build().buildProjectRequest();
      var expectedResponse = ProjectTestData.builder().build().buildProjectResponse();
      var project = ProjectTestData.builder().build().buildProject();
      var userProfile = UserProfileTestData.builder().build().buildUserProfile();

      when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
      when(userProfileRepository.findById(projectRequest.owner()))
          .thenReturn(Optional.of(userProfile));

      when(projectRepository.save(project)).thenReturn(project);
      when(projectMapper.toProjectResponse(project)).thenReturn(expectedResponse);

      // when
      var actual = projectService.update(1L, projectRequest);

      // then
      assertEquals(expectedResponse, actual);
    }
  }

  @Nested
  class Delete {
    @Test
    void deleteShouldCallDaoDeleteMethod() {
      // given
      var id = 1L;

      doNothing().when(projectRepository).deleteById(id);

      assertThatNoException().isThrownBy(() -> projectService.delete(id));
    }
  }
}
