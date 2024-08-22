package model.dto.response;

import model.entity.enums.Position;

public record Employee(
        Long id,
        String firstName,
        String lastName,
        Position position,
        Long departmentId,
        String email,
        String password
) {
}
