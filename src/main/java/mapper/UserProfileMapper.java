package mapper;

import model.dto.request.UserProfileRequest;
import model.dto.response.UserProfileResponse;
import model.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "position", expression = "java(new Position(userProfileRequest.position()))")
    @Mapping(target = "department", expression = "java(new Department(userProfileRequest.department()))")
    @Mapping(target = "user", expression = "java(new User(userProfileRequest.user()))")
    UserProfile toUserProfile(UserProfileRequest userProfileRequest);

    @Mapping(target = "position", source = "position.id")
    @Mapping(target = "department", source = "department.id")
    @Mapping(target = "user", source = "user.id")
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);

}