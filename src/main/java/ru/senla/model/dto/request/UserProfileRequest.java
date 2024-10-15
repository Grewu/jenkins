package ru.senla.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
