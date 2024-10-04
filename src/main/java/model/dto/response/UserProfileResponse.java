package model.dto.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record UserProfileResponse(
        Long id,
        String firstName,
        String lastName,
        Long position,
        Long department,
        Long user
) {
}
