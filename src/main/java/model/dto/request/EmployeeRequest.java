package model.dto.request;

import model.entity.enums.Position;

public record EmployeeRequest(
        Long id,
        String firstName,
        String lastName,
        Position position,
        Long departmentId,
        String email,
        String password
) {
}
