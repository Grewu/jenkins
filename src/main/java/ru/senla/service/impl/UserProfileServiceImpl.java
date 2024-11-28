package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.CommentMapper;
import ru.senla.mapper.UserProfileMapper;
import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.model.entity.Department;
import ru.senla.model.entity.Position;
import ru.senla.model.entity.User;
import ru.senla.model.entity.UserProfile;
import ru.senla.repository.api.CommentRepository;
import ru.senla.repository.api.DepartmentRepository;
import ru.senla.repository.api.PositionRepository;
import ru.senla.repository.api.UserProfileRepository;
import ru.senla.repository.api.UserRepository;
import ru.senla.service.api.UserProfileService;

/**
 * Implementation of the UserProfileService interface, providing methods to manage user profiles.
 * This service handles CRUD operations and maps between entities and DTOs.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProfileServiceImpl implements UserProfileService {

  private final UserProfileMapper userProfileMapper;
  private final CommentMapper commentMapper;
  private final UserProfileRepository userProfileRepository;
  private final UserRepository userRepository;
  private final DepartmentRepository departmentRepository;
  private final CommentRepository commentRepository;
  private final PositionRepository positionRepository;

  /**
   * Creates a new user profile based on the provided UserProfileRequest.
   *
   * @param userProfileRequest the request containing details of the user profile to create
   * @return the response containing the created user profile details
   */
  @Override
  @Transactional
  public UserProfileResponse create(UserProfileRequest userProfileRequest) {
    var userProfile = userProfileMapper.toUserProfile(userProfileRequest);
    return userProfileMapper.toUserProfileResponse(userProfileRepository.save(userProfile));
  }

  /**
   * Retrieves a paginated list of all user profiles.
   *
   * @param pageable pagination information
   * @return a page of user profile responses
   */
  @Override
  public Page<UserProfileResponse> getAll(Pageable pageable) {
    return userProfileRepository.findAll(pageable).map(userProfileMapper::toUserProfileResponse);
  }

  /**
   * Retrieves a paginated list of comments associated with a specific user profile.
   *
   * @param userProfileId the ID of the user profile for which comments are retrieved
   * @param pageable pagination information
   * @return a page of comment responses for the specified user profile
   */
  @Override
  public Page<CommentResponse> getAllCommentsByProfileId(Long userProfileId, Pageable pageable) {
    return commentRepository
        .findCommentsByProfileId(userProfileId, pageable)
        .map(commentMapper::toCommentResponse);
  }

  /**
   * Retrieves a user profile by its ID.
   *
   * @param id the ID of the user profile to retrieve
   * @return the response containing the user profile details
   * @throws EntityNotFoundException if the user profile with the given ID is not found
   */
  @Override
  public UserProfileResponse getById(Long id) {
    return userProfileRepository
        .findById(id)
        .map(userProfileMapper::toUserProfileResponse)
        .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));
  }

  /**
   * Updates an existing user profile with the provided details.
   *
   * @param id the ID of the user profile to update
   * @param userProfileRequest the request containing updated user profile details
   * @return the response containing the updated user profile details
   * @throws EntityNotFoundException if the user profile, user, department, or position with the
   *     given ID is not found
   */
  @Override
  @Transactional
  public UserProfileResponse update(Long id, UserProfileRequest userProfileRequest) {
    var currentUserProfile =
        userProfileRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));

    var user =
        userRepository
            .findById(userProfileRequest.user())
            .orElseThrow(() -> new EntityNotFoundException(User.class, id));

    var department =
        departmentRepository
            .findById(userProfileRequest.department())
            .orElseThrow(() -> new EntityNotFoundException(Department.class, id));

    var position =
        positionRepository
            .findById(userProfileRequest.position())
            .orElseThrow(() -> new EntityNotFoundException(Position.class, id));

    currentUserProfile.setFirstName(userProfileRequest.firstName());
    currentUserProfile.setLastName(userProfileRequest.lastName());
    currentUserProfile.setDepartment(department);
    currentUserProfile.setUser(user);
    currentUserProfile.setPosition(position);

    return userProfileMapper.toUserProfileResponse(userProfileRepository.save(currentUserProfile));
  }

  /**
   * Deletes a user profile by its ID.
   *
   * @param id the ID of the user profile to delete
   */
  @Override
  @Transactional
  public void delete(Long id) {
    var userProfile =
        userProfileRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));
    userProfileRepository.deleteById(userProfile.getId());
  }
}
