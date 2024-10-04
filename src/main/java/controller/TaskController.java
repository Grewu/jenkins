package controller;

import lombok.RequiredArgsConstructor;
import model.dto.request.TaskRequest;
import model.dto.response.TaskResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.api.TaskService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = TaskController.TASK_API_PATH)
public class TaskController {

    private final TaskService taskService;
    public static final String TASK_API_PATH = "/api/v0/tasks";

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.create(taskRequest));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id,
                                               @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.update(id, taskRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody TaskRequest taskRequest) {
        taskService.delete(taskRequest);
        return ResponseEntity.noContent().build();
    }

}
