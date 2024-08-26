package model.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import model.entity.enums.Status;

import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record TaskResponse(
        Long id,
        String taskName,
        Long assignedTo,
        LocalDateTime dueDate,
        Status status,
        Long priority
) {
}
