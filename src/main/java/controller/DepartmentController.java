package controller;

import lombok.RequiredArgsConstructor;
import model.dto.request.DepartmentRequest;
import model.dto.response.DepartmentResponse;
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
import service.api.DepartmentService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = DepartmentController.DEPARTMENT_API_PATH)
public class DepartmentController {

    private final DepartmentService departmentService;
    public static final String DEPARTMENT_API_PATH = "/api/v0/departments";


    @PostMapping
    public ResponseEntity<DepartmentResponse> create(@RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.create(departmentRequest));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> update(@PathVariable Long id,
                                                     @RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.update(id, departmentRequest));
    }


    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody DepartmentRequest departmentRequest) {
        departmentService.delete(departmentRequest);
        return ResponseEntity.noContent().build();
    }
}
