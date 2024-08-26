package mapper;

import model.dto.request.DepartmentRequest;
import model.dto.response.DepartmentResponse;
import model.entity.Department;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    public Department toDepartment(DepartmentRequest departmentRequest) {
        return new Department.Builder()
                .setId(departmentRequest.id())
                .setName(departmentRequest.name())
                .build();
    }

    public DepartmentResponse toDepartmentResponse(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName()
        );
    }

    public List<DepartmentResponse> toListOfDepartmentResponse(List<Department> departments) {
        return departments.stream()
                .map(this::toDepartmentResponse)
                .collect(Collectors.toList());
    }
}
