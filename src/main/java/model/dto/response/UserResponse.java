package model.dto.response;

public record UserResponse(
        Long id,
        String email,
        Long role
) {
}

