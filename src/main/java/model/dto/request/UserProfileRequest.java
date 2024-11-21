package model.dto.request;

public record UserProfileRequest(
        String firstName,
        String lastName,
        Long position,
        Long department,
        Long user
) {
}
