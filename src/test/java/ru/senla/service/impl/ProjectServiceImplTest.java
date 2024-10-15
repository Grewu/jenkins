package ru.senla.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.senla.data.ProjectTestData;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.ProjectMapper;
import ru.senla.repository.api.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ProjectRepository projectRepository;

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
        void getAllShouldReturnListOfProjectResponse() {
            // given
            var pageable = Pageable.ofSize(2);
            var projects = List.of(ProjectTestData.builder().build().buildProject());
            var expectedResponses = List.of(ProjectTestData.builder().build().buildProjectResponse());
            var projectPage = new PageImpl<>(projects,pageable,2);

            doReturn(projectPage)
                    .when(projectRepository).findAll(pageable);

            IntStream.range(0, projects.size())
                    .forEach(i -> doReturn(expectedResponses.get(i))
                            .when(projectMapper).toProjectResponse(projects.get(i)));

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
            var exception = assertThrows(EntityNotFoundException.class,
                    () -> projectService.getById(id));

            // then
            assertEquals("Project with ID -1 was not found", exception.getMessage());
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

            when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
            when(projectMapper.update(projectRequest, project)).thenReturn(project);
            when(projectRepository.save(project)).thenReturn(project);
            when(projectMapper.toProjectResponse(project)).thenReturn(expected);

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
            var id = 1L;

            doNothing()
                    .when(projectRepository).deleteById(id);

            assertThatNoException()
                    .isThrownBy(() -> projectService.delete(id));
        }
    }
}
