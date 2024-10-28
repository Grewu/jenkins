package ru.senla.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;

import java.time.LocalDateTime;

/**
 * DTO for creating a task request.
 * <p>
 * This class holds the essential information needed to create a task, including its name, assigned user,
 * creator, project association, due date, status, and priority level.
 * </p>
 */
public record TaskRequest(
        @NotBlank(message = "Name must not be blank")
        String name,

        @NotNull(message = "Assigned user ID must not be null")
        Long assignedTo,

        @NotNull(message = "Creator ID must not be null")
        Long createdBy,

        @NotNull(message = "Project ID must not be null")
        Long project,

        @NotNull(message = "Due date must not be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dueDate,

        @NotNull(message = "Status must not be null")
        StatusType status,

        @NotNull(message = "Priority must not be null")
        PriorityType priority
) {
}
