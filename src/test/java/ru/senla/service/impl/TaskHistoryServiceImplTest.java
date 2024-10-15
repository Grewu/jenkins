package ru.senla.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.senla.data.TaskHistoryTestData;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.TaskHistoryMapper;
import ru.senla.repository.api.TaskHistoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskHistoryServiceImplTest {

    @Mock
    private TaskHistoryMapper taskHistoryMapper;

    @Mock
    private TaskHistoryRepository taskHistoryRepository;

    @InjectMocks
    private TaskHistoryServiceImpl taskHistoryService;

    @Nested
    class Create {
        @Test
        void createShouldReturnTaskHistoryResponse() {
            // given
            var taskHistoryRequest = TaskHistoryTestData.builder().build().buildTaskHistoryRequest();
            var taskHistory = TaskHistoryTestData.builder().build().buildTaskHistory();
            var expected = TaskHistoryTestData.builder().build().buildTaskHistoryResponse();

            when(taskHistoryMapper.toTaskHistory(taskHistoryRequest)).thenReturn(taskHistory);
            when(taskHistoryRepository.save(taskHistory)).thenReturn(taskHistory);
            when(taskHistoryMapper.toTaskHistoryResponse(taskHistory)).thenReturn(expected);

            // when
            var actual = taskHistoryService.create(taskHistoryRequest);

            // then
            verify(taskHistoryRepository).save(taskHistory);
            verify(taskHistoryMapper).toTaskHistory(taskHistoryRequest);
            verify(taskHistoryMapper).toTaskHistoryResponse(taskHistory);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class GetAll {
        @Test
        void getAllShouldReturnListOfTaskHistoryResponses() {
            // given
            var pageable = Pageable.ofSize(2);
            var tasks = List.of(TaskHistoryTestData.builder().build().buildTaskHistory());
            var expectedResponses = List.of(TaskHistoryTestData.builder().build().buildTaskHistoryResponse());

            var taskPage = new PageImpl<>(tasks, pageable, 2);

            doReturn(taskPage)
                    .when(taskHistoryRepository).findAll(pageable);

            IntStream.range(0, tasks.size())
                    .forEach(i -> doReturn(expectedResponses.get(i))
                            .when(taskHistoryMapper).toTaskHistoryResponse(tasks.get(i)));

            // when
            var actualResponses = taskHistoryService.getAll(pageable).getContent();

            // then
            assertEquals(expectedResponses, actualResponses);
        }
    }

    @Nested
    class GetById {
        @Test
        void getByIdShouldReturnExpectedTaskHistory() {
            // given
            var taskHistory = TaskHistoryTestData.builder().build().buildTaskHistory();
            var expected = TaskHistoryTestData.builder().build().buildTaskHistoryResponse();

            when(taskHistoryRepository.findById(taskHistory.getId())).thenReturn(Optional.of(taskHistory));
            when(taskHistoryMapper.toTaskHistoryResponse(taskHistory)).thenReturn(expected);

            // when
            var actual = taskHistoryService.getById(taskHistory.getId());

            // then
            assertEquals(expected, actual);
        }

        @Test
        void getByIdShouldThrowNotFoundException() {
            // given
            var id = -1L;
            // when
            var exception = assertThrows(EntityNotFoundException.class,
                    () -> taskHistoryService.getById(id));

            // then
            assertEquals("TaskHistory with ID -1 was not found", exception.getMessage());
        }
    }

    @Nested
    class Update {
        @Test
        void updateShouldReturnTaskHistoryResponse() {
            // given
            var taskHistoryRequest = TaskHistoryTestData.builder().build().buildTaskHistoryRequest();
            var expected = TaskHistoryTestData.builder().build().buildTaskHistoryResponse();
            var taskHistory = TaskHistoryTestData.builder().build().buildTaskHistory();

            when(taskHistoryRepository.findById(taskHistory.getId())).thenReturn(Optional.of(taskHistory));
            when(taskHistoryMapper.update(taskHistoryRequest, taskHistory)).thenReturn(taskHistory);
            when(taskHistoryRepository.save(taskHistory)).thenReturn(taskHistory);
            when(taskHistoryMapper.toTaskHistoryResponse(taskHistory)).thenReturn(expected);

            // when
            var actual = taskHistoryService.update(1L, taskHistoryRequest);

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
                    .when(taskHistoryRepository).deleteById(id);

            assertThatNoException()
                    .isThrownBy(() -> taskHistoryService.delete(id));
        }
    }

}