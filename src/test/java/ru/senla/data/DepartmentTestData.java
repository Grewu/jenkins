package ru.senla.data;

import lombok.Builder;
import ru.senla.model.dto.request.DepartmentRequest;
import ru.senla.model.dto.response.DepartmentResponse;
import ru.senla.model.entity.Department;
import ru.senla.model.entity.enums.DepartmentType;

@Builder(setterPrefix = "with")
public class DepartmentTestData {
  @Builder.Default private Long id = 1L;

  @Builder.Default private DepartmentType name = DepartmentType.DEVELOPERS;

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
