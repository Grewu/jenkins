package service.impl;

import dao.api.TaskDao;
import data.TaskTestData;
import exception.EntityNotFoundException;
import mapper.TaskMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskMapper mapper;

    @Mock
    private TaskDao taskDao;

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

            when(mapper.toTask(taskRequest)).thenReturn(task);
            when(taskDao.create(task)).thenReturn(task);
            when(mapper.toTaskResponse(task)).thenReturn(expected);

            // when
            var actual = taskService.create(taskRequest);

            // then
            verify(taskDao).create(task);
            verify(mapper).toTask(taskRequest);
            verify(mapper).toTaskResponse(task);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class GetAll {
        @Test
        void getAllShouldReturnListOfTaskResponses() {
            // given
            var tasks = List.of(TaskTestData.builder().build().buildTask());
            var expectedResponses = List.of(TaskTestData.builder().build().buildTaskResponse());

            when(taskDao.findAll()).thenReturn(tasks);
            when(mapper.toTaskResponse(tasks.get(0))).thenReturn(expectedResponses.get(0));

            // when
            var actualResponses = taskService.getAll();

            // then
            verify(taskDao).findAll();
            verify(mapper).toTaskResponse(tasks.get(0));
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

            when(taskDao.findById(task.getId())).thenReturn(Optional.of(task));
            when(mapper.toTaskResponse(task)).thenReturn(expected);

            // when
            var actual = taskService.getById(task.getId());

            // then
            assertEquals(expected, actual);
        }

        @Test
        void getByIdShouldThrowNotFoundException() {
            // given
            var id = -1L;
            var expected = "Task with ID -1 was not found";

            // when
            var exception = assertThrows(EntityNotFoundException.class,
                    () -> taskService.getById(id));

            // then
            assertEquals(expected, exception.getMessage());
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

            when(mapper.toTask(taskRequest)).thenReturn(task);
            when(taskDao.update(task)).thenReturn(task);
            when(mapper.toTaskResponse(task)).thenReturn(expected);

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
            var taskRequest = TaskTestData.builder().build().buildTaskRequest();
            var task = TaskTestData.builder().build().buildTask();

            when(mapper.toTask(taskRequest)).thenReturn(task);

            // when
            taskService.delete(taskRequest);

            // then
            verify(taskDao).delete(task);
        }
    }
}
