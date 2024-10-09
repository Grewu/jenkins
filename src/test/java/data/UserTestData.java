package data;

import lombok.Builder;
import model.dto.request.UserRequest;
import model.dto.response.UserResponse;
import model.entity.Role;
import model.entity.User;
import model.entity.enums.RoleType;

@Builder(setterPrefix = "with")
public class UserTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String email = "user@example.com";

    @Builder.Default
    private String password = "password";

    @Builder.Default
    private Role role = new Role(1L, RoleType.ADMIN);

    public User buildUser() {
        return new User(id, email, password, role);
    }

    public UserRequest buildUserRequest() {
        return new UserRequest(email, password, role.getId());
    }

    public UserResponse buildUserResponse() {
        return new UserResponse(id, email, role.getId());
    }
}