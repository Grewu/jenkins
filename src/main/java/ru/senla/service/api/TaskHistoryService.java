package ru.senla.service.api;

import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.entity.Task;
import ru.senla.service.AbstractService;

public interface TaskHistoryService extends AbstractService<Long, TaskHistoryRequest, TaskHistoryResponse> {
    void recordTaskUpdateHistory(Task taskToSave, Task currentTask);

    void recordTaskCreationHistory(Task task);
}
