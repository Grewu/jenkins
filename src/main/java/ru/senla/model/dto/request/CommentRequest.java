package ru.senla.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO for creating a comment request.
 * <p>
 * This class includes information about a comment, including the associated task,
 * user profile, comment text, and creation timestamp.
 * </p>
 */
public record CommentRequest(
        @NotNull(message = "Task ID must not be null")
        Long task,
        @NotNull(message = "User profile ID must not be null")
        Long usersProfile,
        @NotNull(message = "Comment text must not be null")
        @Size(min = 1, max = 500, message = "Comment text must be between 1 and 500 characters")
        String commentText,
        @NotNull(message = "Created at must not be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt
) {
}
