package model.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ProjectResponse(
        Long id,
        String projectName,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
