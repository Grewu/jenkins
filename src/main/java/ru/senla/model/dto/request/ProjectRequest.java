package ru.senla.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO for creating a project request.
 * <p>
 * This class includes information required to create a project, such as its name, code, description,
 * start and end dates, and the owner's ID.
 * </p>
 */
public record ProjectRequest(
        @NotBlank(message = "Project name must not be blank")
        String name,

        @NotBlank(message = "Project code must not be blank")
        @Size(max = 30, message = "Project code must not exceed 30 characters")
        String projectCode,

        @NotBlank(message = "Description must not be blank")
        String description,

        @NotNull(message = "Start date must not be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startDate,

        @NotNull(message = "End date must not be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime endDate,

        @NotNull(message = "Owner ID must not be null")
        Long owner
) {
}
