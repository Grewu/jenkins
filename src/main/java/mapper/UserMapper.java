package mapper;

import model.dto.request.UserRequest;
import model.dto.response.UserResponse;
import model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(new Role(userRequest.role()))")
    User toUser(UserRequest userRequest);

    @Mapping(target = "role", source = "role.id")
    UserResponse toUserResponse(User user);
}
