package ru.senla.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * DTO for a comment response.
 *
 * <p>This class provides the details of a comment, including its ID, associated task, user profile,
 * comment text, and the date and time it was created.
 */
public record CommentResponse(
    Long id,
    Long task,
    Long usersProfile,
    String commentText,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt) {}
