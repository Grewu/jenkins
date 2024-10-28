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
import ru.senla.model.dto.request.DepartmentRequest;
import ru.senla.model.dto.response.DepartmentResponse;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.service.api.DepartmentService;

/**
 * The DepartmentRestController class provides REST endpoints for managing departments and the users associated with them.
 * This controller supports operations for creating, retrieving, updating, and deleting departments,
 * as well as retrieving user profiles by department ID.
 * Each endpoint has specific authorization requirements and supports JSON media type responses.
 * It uses {@link DepartmentService} to perform business logic for department management.
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li><b>POST /api/v0/departments</b>: Creates a new department.</li>
 *     <li><b>GET /api/v0/departments/{departmentId}/users</b>: Retrieves all user profiles associated with a department.</li>
 *     <li><b>GET /api/v0/departments</b>: Retrieves a paginated list of all departments.</li>
 *     <li><b>GET /api/v0/departments/{id}</b>: Retrieves a department by its ID.</li>
 *     <li><b>PUT /api/v0/departments/{id}</b>: Updates a department with the specified ID.</li>
 *     <li><b>DELETE /api/v0/departments/{id}</b>: Deletes a department with the specified ID.</li>
 * </ul>
 *
 * <p>Authorization Requirements:</p>
 * <ul>
 *     <li><b>department:write</b> permission is required for creating and updating departments.</li>
 *     <li><b>department:read</b> permission is required for retrieving departments.</li>
 *     <li><b>user_profile:read</b> permission is required for retrieving user profiles by department.</li>
 *     <li><b>department:delete</b> permission is required for deleting departments.</li>
 * </ul>
 *
 * @see DepartmentRequest
 * @see DepartmentResponse
 * @see UserProfileResponse
 * @see DepartmentService
 */
@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = DepartmentRestController.DEPARTMENT_API_PATH)
public class DepartmentRestController {

    protected static final String DEPARTMENT_API_PATH = "/api/v0/departments";
    private final DepartmentService departmentService;

    /**
     * Creates a new department.
     *
     * @param departmentRequest the {@link DepartmentRequest} object containing details of the department to create.
     * @return a {@link ResponseEntity} containing the created {@link DepartmentResponse} with HTTP status 201 (Created).
     */
    @PostMapping
    @PreAuthorize("hasAuthority('department:write')")
    public ResponseEntity<DepartmentResponse> create(@Valid @RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.create(departmentRequest));
    }

    /**
     * Retrieves all user profiles associated with a specific department.
     *
     * @param departmentId the ID of the department.
     * @param pageable the pagination information, with a default page size of 20.
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link UserProfileResponse} objects with HTTP status 200 (OK).
     */
    @GetMapping("/{departmentId}/users")
    @PreAuthorize("hasAuthority('department:read') && hasAuthority('user_profile:read')")
    public ResponseEntity<Page<UserProfileResponse>> getAllUsersProfileByDepartmentId(
            @PathVariable Long departmentId,
            @PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.getAllUsersProfileByDepartmentId(departmentId, pageable));
    }

    /**
     * Retrieves a paginated list of all departments.
     *
     * @param pageable the pagination information, with a default page size of 20.
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link DepartmentResponse} objects with HTTP status 200 (OK).
     */
    @GetMapping
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<Page<DepartmentResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.getAll(pageable));
    }

    /**
     * Retrieves a department by its ID.
     *
     * @param id the ID of the department to retrieve.
     * @return a {@link ResponseEntity} containing the {@link DepartmentResponse} with HTTP status 200 (OK).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<DepartmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.getById(id));
    }

    /**
     * Updates an existing department with the specified ID.
     *
     * @param id the ID of the department to update.
     * @param departmentRequest the {@link DepartmentRequest} object containing updated details of the department.
     * @return a {@link ResponseEntity} containing the updated {@link DepartmentResponse} with HTTP status 200 (OK).
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('department:write')")
    public ResponseEntity<DepartmentResponse> update(@PathVariable Long id,
                                                     @Valid @RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.update(id, departmentRequest));
    }

    /**
     * Deletes a department with the specified ID.
     *
     * @param id the ID of the department to delete.
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content) indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('department:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}