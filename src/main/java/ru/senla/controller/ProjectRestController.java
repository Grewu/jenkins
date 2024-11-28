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

/**
 * The ProjectRestController class provides REST endpoints for managing projects within the system.
 * This controller allows clients to create, retrieve, update, and delete project entities and to
 * fetch tasks related to a specific project. Each endpoint has specific authorization requirements
 * and supports JSON responses. It uses {@link ProjectService} to handle the business logic related
 * to project management.
 *
 * <p>Endpoints:
 *
 * <ul>
 *   <li><b>POST /api/v0/projects</b>: Creates a new project.
 *   <li><b>GET /api/v0/projects</b>: Retrieves a paginated list of all projects.
 *   <li><b>GET /api/v0/projects/{id}</b>: Retrieves a specific project by its ID.
 *   <li><b>GET /api/v0/projects/{projectId}/tasks</b>: Retrieves a paginated list of tasks related
 *       to a specific project.
 *   <li><b>PUT /api/v0/projects/{id}</b>: Updates a specific project by its ID.
 *   <li><b>DELETE /api/v0/projects/{id}</b>: Deletes a specific project by its ID.
 * </ul>
 *
 * <p>Authorization Requirements:
 *
 * <ul>
 *   <li><b>project:write</b> permission is required for creating and updating projects.
 *   <li><b>project:read</b> permission is required for retrieving projects.
 *   <li><b>task:read</b> permission is required for retrieving tasks related to a project.
 *   <li><b>project:delete</b> permission is required for deleting projects.
 * </ul>
 *
 * @see ProjectRequest
 * @see ProjectResponse
 * @see TaskResponse
 * @see ProjectService
 */
@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectRestController.PROJECT_API_PATH)
public class ProjectRestController {

  protected static final String PROJECT_API_PATH = "/api/v0/projects";
  private final ProjectService projectService;

  /**
   * Creates a new project.
   *
   * @param projectRequest the {@link ProjectRequest} object containing details of the project to
   *     create.
   * @return a {@link ResponseEntity} containing the created {@link ProjectResponse} with HTTP
   *     status 201 (Created).
   */
  @PostMapping
  @PreAuthorize("hasAuthority('project:write')")
  public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest projectRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(projectService.create(projectRequest));
  }

  /**
   * Retrieves a paginated list of tasks related to a specific project by project ID.
   *
   * @param projectId the ID of the project for which to retrieve related tasks.
   * @param pageable the pagination information, with a default page size of 20.
   * @return a {@link ResponseEntity} containing a {@link Page} of {@link TaskResponse} objects with
   *     HTTP status 200 (OK).
   */
  @GetMapping("/{projectId}/tasks")
  @PreAuthorize("hasAuthority('project:read') && hasAuthority('task:read')")
  public ResponseEntity<Page<TaskResponse>> getAllTaskRelatedToProject(
      @PathVariable Long projectId, @PageableDefault(20) Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(projectService.getAllTaskRelatedToProjectByProjectId(projectId, pageable));
  }

  /**
   * Retrieves a paginated list of all projects.
   *
   * @param pageable the pagination information, with a default page size of 20.
   * @return a {@link ResponseEntity} containing a {@link Page} of {@link ProjectResponse} objects
   *     with HTTP status 200 (OK).
   */
  @GetMapping
  @PreAuthorize("hasAuthority('project:read')")
  public ResponseEntity<Page<ProjectResponse>> getAll(@PageableDefault(20) Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(projectService.getAll(pageable));
  }

  /**
   * Retrieves a specific project by its ID.
   *
   * @param id the ID of the project to retrieve.
   * @return a {@link ResponseEntity} containing the {@link ProjectResponse} with HTTP status 200
   *     (OK).
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('project:read')")
  public ResponseEntity<ProjectResponse> getById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(projectService.getById(id));
  }

  /**
   * Updates an existing project with the specified ID.
   *
   * @param id the ID of the project to update.
   * @param projectRequest the {@link ProjectRequest} object containing updated details of the
   *     project.
   * @return a {@link ResponseEntity} containing the updated {@link ProjectResponse} with HTTP
   *     status 200 (OK).
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('project:write')")
  public ResponseEntity<ProjectResponse> update(
      @PathVariable Long id, @Valid @RequestBody ProjectRequest projectRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(projectService.update(id, projectRequest));
  }

  /**
   * Deletes a project with the specified ID.
   *
   * @param id the ID of the project to delete.
   * @return a {@link ResponseEntity} with HTTP status 204 (No Content) indicating successful
   *     deletion.
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('project:delete')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    projectService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
