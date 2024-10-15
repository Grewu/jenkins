package ru.senla.model.dto.response;

import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;

import java.time.LocalDateTime;

public record TaskHistoryResponse(
        Long id,
        Long task,
        String name,
        Long project,
        Long assignedTo,
        Long createdBy,
        LocalDateTime dueDate,
        StatusType status,
        PriorityType priority,
        Long changedBy,
        LocalDateTime changedDate,
        String changedDescription
) {
}
