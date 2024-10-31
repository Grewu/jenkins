package ru.senla.data;

import lombok.Builder;
import ru.senla.model.dto.request.UserRequest;
import ru.senla.model.dto.response.UserResponse;
import ru.senla.model.entity.Role;
import ru.senla.model.entity.User;
import ru.senla.model.entity.enums.RoleType;

@Builder(setterPrefix = "with")
public class UserTestData {

  @Builder.Default private Long id = 1L;

  @Builder.Default private String email = "user@example.com";

  @Builder.Default private String password = "password";

  @Builder.Default private Role role = new Role(1L, RoleType.USER);

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
