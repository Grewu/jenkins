package ru.senla.service.api;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.senla.model.dto.request.UserRequest;
import ru.senla.model.dto.response.UserResponse;

/**
 * Service interface for managing user-related operations.
 *
 * <p>This interface extends {@link UserDetailsService} to provide user authentication and
 * management functionalities. It includes methods for generating authorization tokens and creating
 * new users.
 */
public interface UserService extends UserDetailsService {

  /**
   * Generates an authorization token for the specified user request.
   *
   * @param userRequest the request containing user details for authorization
   * @return a string representing the authorization token
   */
  String getAuthorizationToken(UserRequest userRequest);

  /**
   * Creates a new user based on the provided user request.
   *
   * @param userRequest the request containing the details of the user to be created
   * @return a {@link UserResponse} containing the details of the created user
   */
  UserResponse create(UserRequest userRequest);
}
