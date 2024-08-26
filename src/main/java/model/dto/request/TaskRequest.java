package model.dto.request;

import model.entity.enums.Status;

import java.time.LocalDateTime;

public record TaskRequest(
        Long id,
        String taskName,
        Long assignedTo,
        Long projectId,
        LocalDateTime dueDate,
        Status status,
        Long priority
) {
}
