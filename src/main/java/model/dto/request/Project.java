package model.dto.request;

import java.time.LocalDateTime;

public record Project(
        String projectName,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
