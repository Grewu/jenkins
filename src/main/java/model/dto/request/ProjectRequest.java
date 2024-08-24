package model.dto.request;

import java.time.LocalDateTime;

public record ProjectRequest(
        String projectName,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
