package service.impl;

import dao.api.ProjectDao;
import data.ProjectTestData;
import exception.EntityNotFoundException;
import mapper.ProjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectMapper mapper;

    @Mock
    private ProjectDao projectDao;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Nested
    class Create {
        @Test
        void createShouldReturnProjectResponse() {
            // given
            var projectRequest = ProjectTestData.builder().build().buildProjectRequest();
            var project = ProjectTestData.builder().build().buildProject();
            var expected = ProjectTestData.builder().build().buildProjectResponse();

            when(mapper.toProject(projectRequest)).thenReturn(project);
            when(projectDao.create(project)).thenReturn(project);
            when(mapper.toProjectResponse(project)).thenReturn(expected);

            // when
            var actual = projectService.create(projectRequest);

            // then
            verify(projectDao).create(project);
            verify(mapper).toProject(projectRequest);
            verify(mapper).toProjectResponse(project);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class GetAll {
        @Test
        void getAllShouldReturnListOfProjectResponse() {
            // given
            var projects = List.of(ProjectTestData.builder().build().buildProject());
            var expectedResponses = List.of(ProjectTestData.builder().build().buildProjectResponse());

            when(projectDao.findAll()).thenReturn(projects);
            when(mapper.toProjectResponse(projects.get(0))).thenReturn(expectedResponses.get(0));

            // when
            var actualResponses = projectService.getAll();

            // then
            verify(projectDao).findAll();
            verify(mapper).toProjectResponse(projects.get(0));
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

            when(projectDao.findById(project.getId())).thenReturn(Optional.of(project));
            when(mapper.toProjectResponse(project)).thenReturn(expected);

            // when
            var actual = projectService.getById(project.getId());

            // then
            assertEquals(expected, actual);
        }

        @Test
        void getByIdShouldThrowNotFoundException() {
            // given
            var id = -1L;
            var expected = "Project with ID -1 was not found";

            // when
            var exception = assertThrows(EntityNotFoundException.class,
                    () -> projectService.getById(id));

            // then
            assertEquals(expected, exception.getMessage());
        }
    }

    @Nested
    class Update {
        @Test
        void updateShouldReturnProjectResponse() {
            // given
            var projectRequest = ProjectTestData.builder().build().buildProjectRequest();
            var expected = ProjectTestData.builder().build().buildProjectResponse();
            var project = ProjectTestData.builder().build().buildProject();

            when(mapper.toProject(projectRequest)).thenReturn(project);
            when(projectDao.update(project)).thenReturn(project);
            when(mapper.toProjectResponse(project)).thenReturn(expected);

            // when
            var actual = projectService.update(1L, projectRequest);

            // then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class Delete {
        @Test
        void deleteShouldCallDaoDeleteMethod() {
            // given
            var projectRequest = ProjectTestData.builder().build().buildProjectRequest();
            var project = ProjectTestData.builder().build().buildProject();

            when(mapper.toProject(projectRequest)).thenReturn(project);

            // when
            projectService.delete(projectRequest);

            // then
            verify(projectDao).delete(project);
        }
    }
}
