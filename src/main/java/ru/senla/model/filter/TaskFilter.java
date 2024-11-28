package ru.senla.model.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;

public record TaskFilter(
    String name,
    Long assignedTo,
    Long createdBy,
    Long project,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dueDate,
    StatusType status,
    PriorityType priority) {}
