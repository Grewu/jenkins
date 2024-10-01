package model.dto.request;

import java.time.LocalDateTime;

public record CommentRequest(
        Long id,
        Long taskId,
        Long employeeId,
        String commentText,
        LocalDateTime createdAt
) {
}
