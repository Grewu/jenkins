package model.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record CommentResponse(
        Long id,
        String commentText,
        LocalDateTime createdAt
) {
}
