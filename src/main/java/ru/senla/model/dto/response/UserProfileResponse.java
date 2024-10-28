package ru.senla.model.dto.response;


/**
 * DTO for a user profile response.
 * <p>
 * This class contains the details of a user profile, including the user's unique identifier,
 * first name, last name, position ID, department ID, and user ID.
 * </p>
 */
public record UserProfileResponse(
        Long id,
        String firstName,
        String lastName,
        Long position,
        Long department,
        Long user
) {
}
