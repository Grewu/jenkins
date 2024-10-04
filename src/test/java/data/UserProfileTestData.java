package data;

import lombok.Builder;
import model.dto.request.UserProfileRequest;
import model.dto.response.UserProfileResponse;
import model.entity.Department;
import model.entity.Position;
import model.entity.Role;
import model.entity.User;
import model.entity.UserProfile;
import model.entity.enums.DepartmentType;
import model.entity.enums.PositionType;
import model.entity.enums.RoleType;

@Builder(setterPrefix = "with")
public class UserProfileTestData {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String firstName = "firstName";

    @Builder.Default
    private String lastName = "lastName";

    @Builder.Default
    private Position position = new Position(1L, PositionType.DEVELOPER);

    @Builder.Default
    private Department department = new Department(1L, DepartmentType.DEVELOPERS);

    @Builder.Default
    private User user = new User(1L, "user@example.com", "password", new Role(1L, RoleType.ADMIN));


    public UserProfile buildUserProfile() {
        return new UserProfile(id, firstName, lastName, position, department, user);
    }

    public UserProfileRequest buildUserProfileRequest() {
        return new UserProfileRequest(firstName, lastName, position.getId(), department.getId(), user.getId());
    }

    public UserProfileResponse buildUserProfileResponse() {
        return new UserProfileResponse(id, firstName, lastName, position.getId(), department.getId(), user.getId());
    }

}
