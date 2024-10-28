package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.model.entity.UserProfile;

/**
 * Mapper interface for converting between {@link UserProfile} entities and their
 * corresponding DTOs, {@link UserProfileRequest} and {@link UserProfileResponse}.
 *
 * <p>
 * This interface utilizes MapStruct to generate the implementation for mapping
 * properties between the entities and DTOs. It is annotated with
 * {@code @Mapper(componentModel = "spring")} to enable Spring's component scanning.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    /**
     * Converts a {@link UserProfileRequest} to a {@link UserProfile} entity.
     *
     * @param userProfileRequest the DTO containing the details of the user profile
     * @return the converted {@link UserProfile} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "position", expression = "java(new Position(userProfileRequest.position()))")
    @Mapping(target = "department", expression = "java(new Department(userProfileRequest.department()))")
    @Mapping(target = "user", expression = "java(new User(userProfileRequest.user()))")
    UserProfile toUserProfile(UserProfileRequest userProfileRequest);

    /**
     * Converts a {@link UserProfile} entity to a {@link UserProfileResponse} DTO.
     *
     * @param userProfile the user profile entity to be converted
     * @return the converted {@link UserProfileResponse} DTO
     */
    @Mapping(target = "position", source = "position.id")
    @Mapping(target = "department", source = "department.id")
    @Mapping(target = "user", source = "user.id")
    UserProfileResponse toUserProfileResponse(UserProfile userProfile);

    /**
     * Updates an existing {@link UserProfile} entity with values from a
     * {@link UserProfileRequest}.
     *
     * @param userProfileRequest the DTO containing updated details for the user profile
     * @param userProfile        the existing {@link UserProfile} entity to be updated
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "position.id", source = "position")
    @Mapping(target = "department.id", source = "department")
    @Mapping(target = "user.id", source = "user")
    UserProfile update(UserProfileRequest userProfileRequest, @MappingTarget UserProfile userProfile);
}
