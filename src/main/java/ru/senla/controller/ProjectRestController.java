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
import ru.senla.model.dto.request.ProjectRequest;
import ru.senla.model.dto.response.ProjectResponse;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.service.api.ProjectService;

@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectRestController.PROJECT_API_PATH)
public class ProjectRestController {
    private final ProjectService projectService;
    protected static final String PROJECT_API_PATH = "/api/v0/projects";


    @PostMapping
    @PreAuthorize("hasAuthority('project:write')")
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest projectRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectService.create(projectRequest));
    }


    @GetMapping("/{projectId}/tasks")
    @PreAuthorize("hasAuthority('project:read') && hasAuthority('task:read')")
    public ResponseEntity<Page<TaskResponse>> getAllTaskRelatedToProject(@PathVariable Long projectId,
                                                                         @PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectService.getAllTaskRelatedToProjectByProjectId(projectId, pageable));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('project:read')")
    public ResponseEntity<Page<ProjectResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectService.getAll(pageable));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('project:read')")
    public ResponseEntity<ProjectResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('project:write')")
    public ResponseEntity<ProjectResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody ProjectRequest projectRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectService.update(id, projectRequest));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('project:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
