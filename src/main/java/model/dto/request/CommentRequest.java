package model.dto.request;

import java.time.LocalDateTime;

public record CommentRequest(
        Long task,
        Long usersProfile,
        String commentText,
        LocalDateTime createdAt
) {
}
