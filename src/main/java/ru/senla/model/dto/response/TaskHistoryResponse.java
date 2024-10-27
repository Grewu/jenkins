package ru.senla.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dueDate,
        StatusType status,
        PriorityType priority,
        Long changedBy,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime changedDate,
        String changedDescription
) {
}
