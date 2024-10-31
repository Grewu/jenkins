package ru.senla.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;

/**
 * DTO for a task history response.
 *
 * <p>This class contains the historical details of a task, including its unique identifier,
 * associated task ID, project, assigned user, creator, due date, status, priority, and change
 * history details.
 */
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
    String changedDescription) {}
