package model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import model.entity.enums.PriorityType;
import model.entity.enums.StatusType;

import java.time.LocalDateTime;

public record TaskRequest(
        String name,
        Long assignedTo,
        Long createdBy,
        Long project,
        LocalDateTime dueDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        StatusType status,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        PriorityType priority
) {
}
