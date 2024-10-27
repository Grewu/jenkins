package ru.senla.data;

import lombok.Builder;
import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.model.entity.Department;
import ru.senla.model.entity.Position;
import ru.senla.model.entity.User;
import ru.senla.model.entity.UserProfile;


@Builder(setterPrefix = "with")
public class UserProfileTestData {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String firstName = "firstName";

    @Builder.Default
    private String lastName = "lastName";

    @Builder.Default
    private Position position = PositionTestData.builder().build().buildPosition();

    @Builder.Default
    private Department department = DepartmentTestData.builder().build().buildDepartment();

    @Builder.Default
    private User user = UserTestData.builder().build().buildUser();


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
