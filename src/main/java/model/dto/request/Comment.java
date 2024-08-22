package model.dto.request;

import java.time.LocalDateTime;

public record Comment(
        String commentText,
        LocalDateTime createdAt
) {
}
