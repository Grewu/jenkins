package controller;

import lombok.RequiredArgsConstructor;
import model.dto.request.ProjectRequest;
import model.dto.response.ProjectResponse;
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
import service.api.ProjectService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectController.PROJECT_API_PATH)
public class ProjectController {
    private final ProjectService projectService;
    public static final String PROJECT_API_PATH = "/api/v0/projects";


    @PostMapping
    public ResponseEntity<ProjectResponse> create(@RequestBody ProjectRequest projectRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectService.create(projectRequest));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> update(@PathVariable Long id,
                                                  @RequestBody ProjectRequest projectRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectService.update(id, projectRequest));
    }


    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody ProjectRequest projectRequest) {
        projectService.delete(projectRequest);
        return ResponseEntity.noContent().build();
    }
}
