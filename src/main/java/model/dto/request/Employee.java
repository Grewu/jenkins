package model.dto.request;

import model.entity.enums.Position;

public record Employee(
        String firstName,
        String lastName,
        Position position,
        Long departmentId,
        String email,
        String password
) {
}
