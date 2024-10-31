package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.CommentMapper;
import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.model.entity.Comment;
import ru.senla.model.entity.Task;
import ru.senla.model.entity.UserProfile;
import ru.senla.repository.api.CommentRepository;
import ru.senla.repository.api.TaskRepository;
import ru.senla.repository.api.UserProfileRepository;
import ru.senla.service.api.CommentService;

/**
 * The CommentServiceImpl class implements the CommentService interface to provide business logic
 * for managing comments. It handles the creation, retrieval, updating, and deletion of comments
 * while ensuring transactional integrity. The service uses a CommentMapper to convert between
 * entity and DTO objects.
 *
 * <p>This service requires interaction with the following repositories:
 *
 * <ul>
 *   <li>CommentRepository: For CRUD operations on comments.
 *   <li>UserProfileRepository: For retrieving user profile data related to comments.
 *   <li>TaskRepository: For retrieving task data related to comments.
 * </ul>
 *
 * @see CommentService
 * @see CommentMapper
 * @see CommentRequest
 * @see CommentResponse
 * @see Comment
 * @see UserProfile
 * @see Task
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final UserProfileRepository userProfileRepository;
  private final TaskRepository taskRepository;
  private final CommentMapper commentMapper;

  /**
   * Creates a new comment based on the provided request data.
   *
   * @param commentRequest the request object containing the details of the comment to be created.
   * @return a CommentResponse object representing the created comment.
   */
  @Override
  @Transactional
  public CommentResponse create(CommentRequest commentRequest) {
    var comment = commentMapper.toComment(commentRequest);
    return commentMapper.toCommentResponse(commentRepository.save(comment));
  }

  /**
   * Retrieves all comments in a paginated format.
   *
   * @param pageable pagination information to control the number of comments returned.
   * @return a Page of CommentResponse objects containing the comments.
   */
  @Override
  public Page<CommentResponse> getAll(Pageable pageable) {
    return commentRepository.findAll(pageable).map(commentMapper::toCommentResponse);
  }

  /**
   * Retrieves a specific comment by its ID.
   *
   * @param id the ID of the comment to retrieve.
   * @return a CommentResponse object representing the retrieved comment.
   * @throws EntityNotFoundException if the comment with the specified ID does not exist.
   */
  @Override
  public CommentResponse getById(Long id) {
    return commentRepository
        .findById(id)
        .map(commentMapper::toCommentResponse)
        .orElseThrow(() -> new EntityNotFoundException(Comment.class, id));
  }

  /**
   * Updates an existing comment identified by its ID with the provided request data.
   *
   * @param id the ID of the comment to update.
   * @param commentRequest the request object containing the updated comment details.
   * @return a CommentResponse object representing the updated comment.
   * @throws EntityNotFoundException if the comment or related entities (UserProfile, Task) do not
   *     exist.
   */
  @Override
  @Transactional
  public CommentResponse update(Long id, CommentRequest commentRequest) {
    var currentComment =
        commentRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(Comment.class, id));

    var userProfile =
        userProfileRepository
            .findById(commentRequest.usersProfile())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(UserProfile.class, commentRequest.usersProfile()));

    var task =
        taskRepository
            .findById(commentRequest.task())
            .orElseThrow(() -> new EntityNotFoundException(Task.class, commentRequest.task()));

    currentComment.setCommentText(commentRequest.commentText());
    currentComment.setCreatedAt(commentRequest.createdAt());
    currentComment.setUsersProfile(userProfile);
    currentComment.setTask(task);

    return commentMapper.toCommentResponse(commentRepository.save(currentComment));
  }

  /**
   * Deletes the comment identified by its ID.
   *
   * @param id the ID of the comment to delete.
   */
  @Override
  @Transactional
  public void delete(Long id) {
    commentRepository.deleteById(id);
  }
}
