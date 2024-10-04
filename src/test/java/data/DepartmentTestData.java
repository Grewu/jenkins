package data;

import lombok.Builder;
import model.dto.request.DepartmentRequest;
import model.dto.response.DepartmentResponse;
import model.entity.Department;
import model.entity.enums.DepartmentType;

@Builder(setterPrefix = "with")
public class DepartmentTestData {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private DepartmentType name = DepartmentType.DEVELOPERS;

    public Department buildDepartment() {
        return new Department(id, name);
    }

    public DepartmentRequest buildDepartmentRequest() {
        return new DepartmentRequest(name);
    }

    public DepartmentResponse buildDepartmentResponse() {
        return new DepartmentResponse(id, name.name());
    }
}
