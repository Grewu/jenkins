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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.annotation.Logging;
import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.service.api.TaskHistoryService;

/**
 * The TaskHistoryRestController class provides REST endpoints for managing task history records.
 * This controller allows clients to retrieve, update, and delete task history entities. Each
 * endpoint has specific authorization requirements and supports JSON responses. It uses {@link
 * TaskHistoryService} to handle the business logic related to task history management.
 *
 * <p>Endpoints:
 *
 * <ul>
 *   <li><b>GET /api/v0/task_history</b>: Retrieves a paginated list of all task history records.
 *   <li><b>GET /api/v0/task_history/{id}</b>: Retrieves a specific task history record by its ID.
 *   <li><b>PUT /api/v0/task_history/{id}</b>: Updates a specific task history record by its ID.
 *   <li><b>DELETE /api/v0/task_history/{id}</b>: Deletes a specific task history record by its ID.
 * </ul>
 *
 * <p>Authorization Requirements:
 *
 * <ul>
 *   <li><b>task_history:read</b> permission is required for retrieving task history records.
 *   <li><b>task_history:write</b> permission is required for updating task history records.
 *   <li><b>task_history:delete</b> permission is required for deleting task history records.
 * </ul>
 *
 * @see TaskHistoryRequest
 * @see TaskHistoryResponse
 * @see TaskHistoryService
 */
@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = TaskHistoryRestController.TASK_API_PATH)
public class TaskHistoryRestController {

  protected static final String TASK_API_PATH = "/api/v0/task_history";
  private final TaskHistoryService taskHistoryService;

  /**
   * Retrieves a paginated list of all task history records.
   *
   * @param pageable the pagination information, with a default page size of 20.
   * @return a {@link ResponseEntity} containing a {@link Page} of {@link TaskHistoryResponse}
   *     objects with HTTP status 200 (OK).
   */
  @GetMapping
  @PreAuthorize("hasAuthority('task_history:read')")
  public ResponseEntity<Page<TaskHistoryResponse>> getAll(@PageableDefault(20) Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(taskHistoryService.getAll(pageable));
  }

  /**
   * Retrieves a specific task history record by its ID.
   *
   * @param id the ID of the task history record to retrieve.
   * @return a {@link ResponseEntity} containing the {@link TaskHistoryResponse} with HTTP status
   *     200 (OK).
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('task_history:read')")
  public ResponseEntity<TaskHistoryResponse> getById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(taskHistoryService.getById(id));
  }

  /**
   * Updates an existing task history record with the specified ID.
   *
   * @param id the ID of the task history record to update.
   * @param taskHistoryRequest the {@link TaskHistoryRequest} object containing updated details of
   *     the task history record.
   * @return a {@link ResponseEntity} containing the updated {@link TaskHistoryResponse} with HTTP
   *     status 200 (OK).
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('task_history:write')")
  public ResponseEntity<TaskHistoryResponse> update(
      @PathVariable Long id, @Valid @RequestBody TaskHistoryRequest taskHistoryRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(taskHistoryService.update(id, taskHistoryRequest));
  }

  /**
   * Deletes a task history record with the specified ID.
   *
   * @param id the ID of the task history record to delete.
   * @return a {@link ResponseEntity} with HTTP status 204 (No Content) indicating successful
   *     deletion.
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('task_history:delete')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    taskHistoryService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
