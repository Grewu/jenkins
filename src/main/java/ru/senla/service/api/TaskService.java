package ru.senla.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.senla.model.dto.request.TaskRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.model.filter.TaskFilter;
import ru.senla.service.AbstractService;

public interface TaskService extends AbstractService<Long, TaskRequest, TaskResponse> {
    Page<TaskResponse> getAllByFilter(TaskFilter taskFilter, Pageable pageable);

    Page<TaskHistoryResponse> getAllTaskHistory(Long taskId, Pageable pageable);
}
