package ru.senla.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.senla.model.dto.request.TaskRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.model.filter.TaskFilter;
import ru.senla.service.AbstractService;

/**
 * Service interface for managing tasks within the application.
 * <p>
 * This interface defines the operations for creating, updating, deleting, and retrieving tasks.
 * It extends the {@link AbstractService} for generic CRUD operations and adds task-specific functionality.
 * </p>
 */
public interface TaskService extends AbstractService<Long, TaskRequest, TaskResponse> {

    /**
     * Retrieves all tasks that match the specified filter criteria.
     *
     * @param taskFilter the filter criteria to apply when retrieving tasks
     * @param pageable   pagination information including page number and size
     * @return a paginated list of tasks that match the filter criteria
     */
    Page<TaskResponse> getAllByFilter(TaskFilter taskFilter, Pageable pageable);

    /**
     * Retrieves the history of changes for a specific task.
     *
     * @param taskId   the ID of the task for which history is to be retrieved
     * @param pageable  pagination information including page number and size
     * @return a paginated list of task history records for the specified task
     */
    Page<TaskHistoryResponse> getAllTaskHistory(Long taskId, Pageable pageable);
}
