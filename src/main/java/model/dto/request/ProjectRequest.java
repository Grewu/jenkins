package model.dto.request;

import java.time.LocalDateTime;

public record ProjectRequest(
        String name,
        String projectCode,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long owner
) {
}
