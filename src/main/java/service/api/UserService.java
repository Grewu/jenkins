package service.api;

import model.dto.request.UserRequest;
import model.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String getAuthorizationToken(UserRequest userRequest);

    UserResponse create(UserRequest userRequest);
}