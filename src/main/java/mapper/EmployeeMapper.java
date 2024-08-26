package mapper;

import model.dto.request.EmployeeRequest;
import model.dto.response.EmployeeResponse;
import model.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public Employee toEmployee(EmployeeRequest employeeRequest) {
        return new Employee.Builder()
                .setId(employeeRequest.id())
                .setFirstName(employeeRequest.firstName())
                .setLastName(employeeRequest.lastName())
                .setPosition(employeeRequest.position())
                .setDepartmentId(employeeRequest.departmentId())
                .setEmail(employeeRequest.email())
                .setPassword(employeeRequest.password())
                .build();
    }

    public EmployeeResponse toEmployeeResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPosition(),
                employee.getDepartmentId(),
                employee.getEmail()
        );
    }

    public List<EmployeeResponse> toListOfEmployeeResponse(List<Employee> employees) {
        return employees.stream()
                .map(this::toEmployeeResponse)
                .collect(Collectors.toList());
    }
}
