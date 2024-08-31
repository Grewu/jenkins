package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.request.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.api.EmployeeService;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    public String create(EmployeeRequest employeeRequest) throws IOException {
        return objectMapper.writeValueAsString(employeeService.create(employeeRequest));
    }

    public String getAll() throws IOException {
        return objectMapper.writeValueAsString(employeeService.getAll());
    }

    public String getById(Long id) throws IOException {
        return objectMapper.writeValueAsString(employeeService.getById(id));
    }

    public String update(EmployeeRequest employeeRequest) throws IOException {
        return objectMapper.writeValueAsString(employeeService.update(employeeRequest));
    }

    public boolean delete(Long id) {
        return employeeService.delete(id);
    }

    public String getAllEmployeesWithComments() throws IOException {
        return objectMapper.writeValueAsString(employeeService.getAllEmployeesWithComments());
    }

    public String getEmployeesCommentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        return objectMapper.writeValueAsString(employeeService.getEmployeeCommentsByDateRange(startDate, endDate));
    }

    public String getEmployeesCommentsByDepartmentId(Long departmentId) throws IOException {
        return objectMapper.writeValueAsString(employeeService.getEmployeeCommentsByDepartmentId(departmentId));
    }
}
