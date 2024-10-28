package ru.senla.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;

import java.time.LocalDateTime;

/**
 * DTO for creating a task history request.
 * <p>
 * This class contains details about a task's history, including its name, associated project,
 * assigned user, creator, due date, status, priority, and information on changes made.
 * </p>
 */
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dueDate,

        @NotNull(message = "Status must not be null")
        StatusType status,

        @NotNull(message = "Priority must not be null")
        PriorityType priority,

        @NotNull(message = "Changed by user ID must not be null")
        Long changedBy,

        @NotNull(message = "Changed date must not be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime changedDate,

        @NotBlank(message = "Changed description must not be blank")
        String changedDescription
) {
}
