package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.model.entity.UserProfile;

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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "position.id", source = "position")
    @Mapping(target = "department.id", source = "department")
    @Mapping(target = "user.id", source = "user")
    UserProfile update(UserProfileRequest userProfileRequest, @MappingTarget UserProfile userProfile);
}