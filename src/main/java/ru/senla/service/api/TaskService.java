package ru.senla.service.api;

import ru.senla.model.dto.request.TaskRequest;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.service.AbstractService;

public interface TaskService extends AbstractService<Long, TaskRequest, TaskResponse> {
}
