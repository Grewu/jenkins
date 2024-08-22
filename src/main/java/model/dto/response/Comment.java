package model.dto.response;

import java.time.LocalDateTime;

public record Comment(
        Long id,
        String commentText,
        LocalDateTime createdAt
) {
}
