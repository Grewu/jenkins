package ru.senla.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;

/**
 * DTO for a task response.
 *
 * <p>This class represents the details of a task, including its unique identifier, name, associated
 * project, assigned user, creator, due date, status, and priority.
 */
public record TaskResponse(
    Long id,
    String name,
    Long project,
    Long assignedTo,
    Long createdBy,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dueDate,
    StatusType status,
    PriorityType priority) {}
