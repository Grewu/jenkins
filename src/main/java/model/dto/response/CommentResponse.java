package model.dto.response;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String commentText,
        LocalDateTime createdAt
) {
}
