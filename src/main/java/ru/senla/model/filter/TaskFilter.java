package ru.senla.model.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;

import java.time.LocalDateTime;

public record TaskFilter(
        String name,

        Long assignedTo,

        Long createdBy,

        Long project,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime dueDate,

        StatusType status,

        PriorityType priority
) {
}
