package service.api;

import model.dto.request.TaskRequest;
import model.dto.response.TaskResponse;
import service.AbstractService;

public interface TaskService extends AbstractService<Long, TaskRequest, TaskResponse> {
}
