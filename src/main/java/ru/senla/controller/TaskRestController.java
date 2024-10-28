package ru.senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.annotation.Logging;
import ru.senla.model.dto.request.TaskRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.model.filter.TaskFilter;
import ru.senla.service.api.TaskService;

/**
 * The TaskRestController class provides REST endpoints for managing tasks.
 * This controller supports creating, retrieving, updating, and deleting tasks,
 * as well as filtering tasks and retrieving task history.
 * Each endpoint has specific authorization requirements and supports JSON responses.
 * It uses {@link TaskService} to handle the business logic related to task management.
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li><b>POST /api/v0/tasks</b>: Creates a new task.</li>
 *     <li><b>GET /api/v0/tasks</b>: Retrieves a paginated list of all tasks.</li>
 *     <li><b>GET /api/v0/tasks/filter</b>: Retrieves a paginated list of tasks based on filter criteria.</li>
 *     <li><b>GET /api/v0/tasks/{taskId}/history</b>: Retrieves the history of a specific task by task ID.</li>
 *     <li><b>GET /api/v0/tasks/{id}</b>: Retrieves a specific task by its ID.</li>
 *     <li><b>PUT /api/v0/tasks/{id}</b>: Updates a specific task by its ID.</li>
 *     <li><b>DELETE /api/v0/tasks/{id}</b>: Deletes a specific task by its ID.</li>
 * </ul>
 *
 * <p>Authorization Requirements:</p>
 * <ul>
 *     <li><b>task:read</b> permission is required for retrieving and viewing tasks and task history.</li>
 *     <li><b>task:write</b> permission is required for creating and updating tasks.</li>
 *     <li><b>task:delete</b> permission is required for deleting tasks.</li>
 *     <li><b>task_history:read</b> permission is required in combination with <b>task:read</b>
 *         to retrieve task history.</li>
 * </ul>
 *
 * @see TaskRequest
 * @see TaskResponse
 * @see TaskHistoryResponse
 * @see TaskService
 */
@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = TaskRestController.TASK_API_PATH)
public class TaskRestController {

    protected static final String TASK_API_PATH = "/api/v0/tasks";
    private final TaskService taskService;

    /**
     * Creates a new task.
     *
     * @param taskRequest the {@link TaskRequest} object containing task details.
     * @return a {@link ResponseEntity} containing the created {@link TaskResponse} with HTTP status 201 (Created).
     */
    @PostMapping
    @PreAuthorize("hasAuthority('task:write')")
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.create(taskRequest));
    }

    /**
     * Retrieves a paginated list of all tasks.
     *
     * @param pageable the pagination information, with a default page size of 20.
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link TaskResponse} objects with HTTP status 200 (OK).
     */
    @GetMapping
    @PreAuthorize("hasAuthority('task:read')")
    public ResponseEntity<Page<TaskResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getAll(pageable));
    }

    /**
     * Retrieves a paginated list of tasks based on specified filter criteria.
     *
     * @param taskFilter the {@link TaskFilter} object containing filter criteria.
     * @param pageable   the pagination information, with a default page size of 20.
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link TaskResponse} objects with HTTP status 200 (OK).
     */
    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('task:read')")
    public ResponseEntity<Page<TaskResponse>> getAllByFilter(@Valid TaskFilter taskFilter,
                                                             @PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getAllByFilter(taskFilter, pageable));
    }

    /**
     * Retrieves the history of a specific task by its ID.
     *
     * @param taskId   the ID of the task whose history is to be retrieved.
     * @param pageable the pagination information, with a default page size of 20.
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link TaskHistoryResponse} objects with HTTP status 200 (OK).
     */
    @GetMapping("/{taskId}/history")
    @PreAuthorize("hasAuthority('task:read') && hasAuthority('task_history:read')")
    public ResponseEntity<Page<TaskHistoryResponse>> getAllTaskHistoryByTaskId(
            @PathVariable Long taskId,
            @PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getAllTaskHistory(taskId, pageable));
    }

    /**
     * Retrieves a specific task by its ID.
     *
     * @param id the ID of the task to retrieve.
     * @return a {@link ResponseEntity} containing the {@link TaskResponse} with HTTP status 200 (OK).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('task:read')")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getById(id));
    }

    /**
     * Updates an existing task with the specified ID.
     *
     * @param id          the ID of the task to update.
     * @param taskRequest the {@link TaskRequest} object containing updated task details.
     * @return a {@link ResponseEntity} containing the updated {@link TaskResponse} with HTTP status 200 (OK).
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('task:write')")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.update(id, taskRequest));
    }

    /**
     * Deletes a task with the specified ID.
     *
     * @param id the ID of the task to delete.
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content) indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('task:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
