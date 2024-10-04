package model.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import model.entity.enums.PriorityType;
import model.entity.enums.StatusType;

import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record TaskResponse(
        Long id,
        String name,
        Long project,
        Long assignedTo,
        Long createdBy,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dueDate,
        StatusType status,
        PriorityType priority
) {
}
