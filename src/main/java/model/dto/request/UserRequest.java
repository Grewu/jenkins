package model.dto.request;

public record UserRequest(
        String email,
        String password,
        Long role
) {
}

