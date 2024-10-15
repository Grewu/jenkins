package ru.senla.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;

import java.time.LocalDateTime;

public record TaskHistoryRequest(
        @NotNull(message = "Task ID must not be null")
        Long task,

        @NotBlank(message = "Name must not be blank")
        String name,

        @NotNull(message = "Project ID must not be null")
        Long project,

        @NotNull(message = "Assigned user ID must not be null")
        Long assignedTo,

        @NotNull(message = "Creator ID must not be null")
        Long createdBy,

        @NotNull(message = "Due date must not be null")
        LocalDateTime dueDate,

        @NotNull(message = "Status must not be null")
        StatusType status,

        @NotNull(message = "Priority must not be null")
        PriorityType priority,

        @NotNull(message = "Changed by user ID must not be null")
        Long changedBy,

        @NotNull(message = "Changed date must not be null")
        LocalDateTime changedDate,

        @NotBlank(message = "Changed description must not be blank")
        String changedDescription
) {
}
