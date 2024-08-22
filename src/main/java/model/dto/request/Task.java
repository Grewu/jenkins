package model.dto.request;

import model.entity.enums.Status;

import java.time.LocalDateTime;

public record Task(
        String taskName,
        Long assignedTo,
        LocalDateTime dueDate,
        Status status,
        Long priority
) {
}
