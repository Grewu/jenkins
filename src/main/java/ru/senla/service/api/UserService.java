package ru.senla.service.api;

import ru.senla.model.dto.request.UserRequest;
import ru.senla.model.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String getAuthorizationToken(UserRequest userRequest);

    UserResponse create(UserRequest userRequest);
}