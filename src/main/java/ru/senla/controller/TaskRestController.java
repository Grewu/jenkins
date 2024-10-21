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

@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = TaskRestController.TASK_API_PATH)
public class TaskRestController {

    private final TaskService taskService;
    protected static final String TASK_API_PATH = "/api/v0/tasks";

    @PostMapping
    @PreAuthorize("hasAuthority('task:write')")
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.create(taskRequest));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('task:read')")
    public ResponseEntity<Page<TaskResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getAll(pageable));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('task:read')")
    public ResponseEntity<Page<TaskResponse>> getAllByFilter(@Valid TaskFilter taskFilter,
                                                             @PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getAllByFilter(taskFilter, pageable));
    }


    @GetMapping("/{taskId}/history")
    @PreAuthorize("hasAuthority('task:read') && hasAuthority('task_history:read')")
    public ResponseEntity<Page<TaskHistoryResponse>> getAllTaskHistoryByTaskId(@PathVariable Long taskId,
                                                                               @PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getAllTaskHistory(taskId, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('task:read')")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('task:write')")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.update(id, taskRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('task:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
