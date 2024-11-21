package mapper;

import model.dto.request.DepartmentRequest;
import model.dto.response.DepartmentResponse;
import model.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Department toDepartment(DepartmentRequest departmentRequest);

    DepartmentResponse toDepartmentResponse(Department department);
}
