package model.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import model.entity.enums.Position;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        Position position,
        Long departmentId,
        String email
) {
}
