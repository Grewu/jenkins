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

@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = DepartmentRestController.DEPARTMENT_API_PATH)
public class DepartmentRestController {

    private final DepartmentService departmentService;
    protected static final String DEPARTMENT_API_PATH = "/api/v0/departments";


    @PostMapping
    @PreAuthorize("hasAuthority('department:write')")
    public ResponseEntity<DepartmentResponse> create(@Valid @RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.create(departmentRequest));
    }

    @GetMapping("/{departmentId}/users")
    @PreAuthorize("hasAuthority('department:read') && hasAuthority('user_profile:read')")
    public ResponseEntity<Page<UserProfileResponse>> getAllUsersProfileByDepartmentId(@PathVariable Long departmentId,
                                                                                      @PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.getAllUsersProfileByDepartmentId(departmentId, pageable));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<Page<DepartmentResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.getAll(pageable));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<DepartmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('department:write')")
    public ResponseEntity<DepartmentResponse> update(@PathVariable Long id,
                                                     @Valid @RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.update(id, departmentRequest));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('department:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
