package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.request.DepartmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.api.DepartmentService;

import java.io.IOException;

@Controller
public class DepartmentController {

    private final DepartmentService departmentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public DepartmentController(DepartmentService departmentService, ObjectMapper objectMapper) {
        this.departmentService = departmentService;
        this.objectMapper = objectMapper;
    }


    public String create(DepartmentRequest departmentRequest) throws IOException {
        return objectMapper.writeValueAsString(departmentService.create(departmentRequest));
    }

    public String getAll() throws IOException {
        return objectMapper.writeValueAsString(departmentService.getAll());
    }

    public String getById(Long id) throws IOException {
        return objectMapper.writeValueAsString(departmentService.getById(id));
    }

    public String update(DepartmentRequest departmentRequest) throws IOException {
        return objectMapper.writeValueAsString(departmentService.update(departmentRequest));
    }

    public void delete(Long id) {
        departmentService.delete(id);
    }
}
