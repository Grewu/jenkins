package ru.senla.model.dto.response;


public record UserProfileResponse(
        Long id,
        String firstName,
        String lastName,
        Long position,
        Long department,
        Long user
) {
}
