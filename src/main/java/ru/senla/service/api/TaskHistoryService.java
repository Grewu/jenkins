package ru.senla.service.api;

import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.service.AbstractService;

/**
 * Service interface for managing task histories within the application.
 *
 * <p>This interface defines the operations for creating, retrieving, updating, and deleting task
 * history records. It extends the abstract service interface to provide a consistent way to manage
 * task histories.
 */
public interface TaskHistoryService
    extends AbstractService<Long, TaskHistoryRequest, TaskHistoryResponse> {}
