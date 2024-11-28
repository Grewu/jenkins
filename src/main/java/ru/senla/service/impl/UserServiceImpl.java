package ru.senla.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.exception.InvalidEmailException;
import ru.senla.exception.InvalidPasswordException;
import ru.senla.mapper.UserMapper;
import ru.senla.model.dto.request.UserRequest;
import ru.senla.model.dto.response.UserResponse;
import ru.senla.model.entity.User;
import ru.senla.repository.api.UserRepository;
import ru.senla.service.api.TokenService;
import ru.senla.service.api.UserService;

/**
 * Implementation of the UserService interface, providing methods to manage users, including user
 * creation and authentication.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  /**
   * Generates an authorization token for a user based on their email and password.
   *
   * @param userRequest the request containing the user's email and password
   * @return the generated authorization token
   * @throws InvalidEmailException if the provided email does not exist
   * @throws InvalidPasswordException if the provided password is incorrect
   */
  @Override
  public String getAuthorizationToken(UserRequest userRequest) {
    return userRepository
        .findByEmail(userRequest.email())
        .map(
            u -> {
              if (passwordEncoder.matches(userRequest.password(), u.getPassword())) {
                return tokenService.generateToken(u);
              } else {
                throw new InvalidPasswordException(userRequest.password());
              }
            })
        .orElseThrow(() -> new InvalidEmailException(userRequest.email()));
  }

  /**
   * Loads a user by their email address.
   *
   * @param email the email address of the user
   * @return the UserDetails object for the user
   * @throws EntityNotFoundException if the user with the given email does not exist
   */
  @Override
  public UserDetails loadUserByUsername(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException(User.class, email));
  }

  /**
   * Creates a new user based on the provided UserRequest.
   *
   * @param userRequest the request containing user details for creation
   * @return the response containing the created user details
   * @throws InvalidEmailException if the email is already in use or invalid
   */
  @Override
  @Transactional
  public UserResponse create(UserRequest userRequest) {
    try {
      return Optional.of(userRequest)
          .map(userMapper::toUser)
          .map(
              u -> {
                u.setPassword(passwordEncoder.encode(userRequest.password()));
                return u;
              })
          .map(userRepository::save)
          .map(userMapper::toUserResponse)
          .orElseThrow();
    } catch (DataAccessException e) {
      throw new InvalidEmailException(userRequest.email());
    }
  }
}
