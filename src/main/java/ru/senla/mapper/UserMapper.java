package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.senla.model.dto.request.UserRequest;
import ru.senla.model.dto.response.UserResponse;
import ru.senla.model.entity.User;

/**
 * Mapper interface for converting between {@link User} entities and their
 * corresponding DTOs, {@link UserRequest} and {@link UserResponse}.
 *
 * <p>
 * This interface utilizes MapStruct to generate the implementation for mapping
 * properties between the entities and DTOs. It is annotated with
 * {@code @Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)}
 * to enable Spring's component scanning and ignore any unmapped target properties.
 * </p>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Converts a {@link UserRequest} to a {@link User} entity.
     *
     * @param userRequest the DTO containing the details of the user
     * @return the converted {@link User} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(new Role(userRequest.role()))")
    User toUser(UserRequest userRequest);

    /**
     * Converts a {@link User} entity to a {@link UserResponse} DTO.
     *
     * @param user the user entity to be converted
     * @return the converted {@link UserResponse} DTO
     */
    @Mapping(target = "role", source = "role.id")
    UserResponse toUserResponse(User user);
}
