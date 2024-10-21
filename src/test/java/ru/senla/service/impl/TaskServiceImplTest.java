package ru.senla.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.senla.data.ProjectTestData;
import ru.senla.data.TaskFilterTestData;
import ru.senla.data.TaskTestData;
import ru.senla.data.UserProfileTestData;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.TaskMapper;
import ru.senla.repository.api.ProjectRepository;
import ru.senla.repository.api.TaskRepository;
import ru.senla.repository.api.UserProfileRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskHistoryServiceImpl taskHistoryService;

    @InjectMocks
    private TaskServiceImpl taskService;


    @Nested
    class Create {
        @Test
        void createShouldReturnTaskResponse() {
            // given
            var taskRequest = TaskTestData.builder().build().buildTaskRequest();
            var task = TaskTestData.builder().build().buildTask();
            var expected = TaskTestData.builder().build().buildTaskResponse();

            when(taskMapper.toTask(taskRequest)).thenReturn(task);
            when(taskRepository.save(task)).thenReturn(task);
            when(taskMapper.toTaskResponse(task)).thenReturn(expected);

            // when
            var actual = taskService.create(taskRequest);

            // then
            verify(taskRepository).save(task);
            verify(taskMapper).toTask(taskRequest);
            verify(taskMapper).toTaskResponse(task);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class GetAll {
        @Test
        void getAllByFilter() {
            var pageable = Pageable.ofSize(2);
            var taskFilter = TaskFilterTestData.builder().build().buildTaskFilter();
            var tasks = List.of(TaskTestData.builder().build().buildTask());
            var taskPage = new PageImpl<>(tasks, pageable, 2);
            var expectedResponses = List.of(TaskTestData.builder().build().buildTaskResponse());

            doReturn(taskPage)
                    .when(taskRepository).findAll(any(Specification.class), eq(pageable));

            IntStream.range(0, tasks.size())
                    .forEach(i -> doReturn(expectedResponses.get(i))
                            .when(taskMapper).toTaskResponse(tasks.get(i)));

            var actualResponse =
                    taskService.getAllByFilter(taskFilter, pageable).getContent();

            assertThat(actualResponse).isEqualTo(expectedResponses);
        }


        @Test
        void getAllShouldReturnListOfTaskResponses() {
            // given
            var pageable = Pageable.ofSize(2);
            var tasks = List.of(TaskTestData.builder().build().buildTask());
            var expectedResponses = List.of(TaskTestData.builder().build().buildTaskResponse());

            var taskPage = new PageImpl<>(tasks, pageable, 2);

            doReturn(taskPage)
                    .when(taskRepository).findAll(pageable);

            IntStream.range(0, tasks.size())
                    .forEach(i -> doReturn(expectedResponses.get(i))
                            .when(taskMapper).toTaskResponse(tasks.get(i)));

            // when
            var actualResponses = taskService.getAll(pageable).getContent();

            // then
            assertEquals(expectedResponses, actualResponses);
        }
    }

    @Nested
    class GetById {
        @Test
        void getByIdShouldReturnExpectedTask() {
            // given
            var task = TaskTestData.builder().build().buildTask();
            var expected = TaskTestData.builder().build().buildTaskResponse();

            when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            when(taskMapper.toTaskResponse(task)).thenReturn(expected);

            // when
            var actual = taskService.getById(task.getId());

            // then
            assertEquals(expected, actual);
        }

        @Test
        void getByIdShouldThrowNotFoundException() {
            // given
            var id = -1L;
            // when
            var exception = assertThrows(EntityNotFoundException.class,
                    () -> taskService.getById(id));

            // then
            assertEquals("Task with ID -1 was not found", exception.getMessage());
        }
    }

    @Nested
    class Update {
        @Test
        void updateShouldReturnTaskResponse() {
            // given
            var taskRequest = TaskTestData.builder().build().buildTaskRequest();
            var expected = TaskTestData.builder().build().buildTaskResponse();
            var task = TaskTestData.builder().build().buildTask();
            var userProfile = UserProfileTestData.builder().build().buildUserProfile();
            var project = ProjectTestData.builder().build().buildProject();

            when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
            when(userProfileRepository.findById(taskRequest.project())).thenReturn(Optional.of(userProfile));
            when(projectRepository.findById(taskRequest.project())).thenReturn(Optional.of(project));

            when(taskMapper.update(taskRequest, task)).thenReturn(task);
            when(taskRepository.save(task)).thenReturn(task);
            when(taskMapper.toTaskResponse(task)).thenReturn(expected);

            // when
            var actual = taskService.update(1L, taskRequest);

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
                    .when(taskRepository).deleteById(id);

            assertThatNoException()
                    .isThrownBy(() -> taskService.delete(id));
        }
    }
}
