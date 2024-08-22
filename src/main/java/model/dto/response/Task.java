package model.dto.response;

import model.entity.enums.Status;

import java.time.LocalDateTime;

public record Task(
        Long id,
        String taskName,
        Long assignedTo,
        LocalDateTime dueDate,
        Status status,
        Long priority
) {
}
