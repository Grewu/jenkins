package ru.senla.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a user profile request.
 * <p>
 * This class contains the necessary information for creating a user profile, including first name, last name,
 * and associations with position, department, and user details.
 * </p>
 */
public record UserProfileRequest(
        @NotBlank(message = "First name must not be blank")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        String lastName,

        @NotNull(message = "Position ID must not be null")
        Long position,

        @NotNull(message = "Department ID must not be null")
        Long department,

        @NotNull(message = "User ID must not be null")
        Long user
) {
}
