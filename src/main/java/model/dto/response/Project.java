package model.dto.response;

import java.time.LocalDateTime;

public record Project(
        Long id,
        String projectName,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
