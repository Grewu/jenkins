package model.dto.response;

import model.entity.enums.Position;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        Position position,
        Long departmentId,
        String email
) {
}
