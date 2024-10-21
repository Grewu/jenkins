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

@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = TaskHistoryRestController.TASK_API_PATH)
public class TaskHistoryRestController {

    private final TaskHistoryService taskHistoryService;
    protected static final String TASK_API_PATH = "/api/v0/task_history";

    @GetMapping
    @PreAuthorize("hasAuthority('task_history:read')")
    public ResponseEntity<Page<TaskHistoryResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskHistoryService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('task_history:read')")
    public ResponseEntity<TaskHistoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskHistoryService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('task_history:write')")
    public ResponseEntity<TaskHistoryResponse> update(@PathVariable Long id,
                                                      @Valid @RequestBody TaskHistoryRequest taskHistoryRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskHistoryService.update(id, taskHistoryRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('task_history:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskHistoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
