package ru.senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.annotation.Logging;
import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.service.api.CommentService;

/**
 * The CommentRestController class provides REST endpoints for managing comments. This controller
 * supports operations for creating, retrieving, updating, and deleting comments. Each endpoint has
 * specific authorization requirements and supports JSON media type responses. It uses {@link
 * CommentService} to perform business logic for comment management.
 *
 * <p>Endpoints:
 *
 * <ul>
 *   <li><b>POST /api/v0/comments</b>: Creates a new comment.
 *   <li><b>GET /api/v0/comments</b>: Retrieves a paginated list of all comments.
 *   <li><b>GET /api/v0/comments/{id}</b>: Retrieves a comment by its ID.
 *   <li><b>PUT /api/v0/comments/{id}</b>: Updates a comment with the specified ID.
 *   <li><b>DELETE /api/v0/comments/{id}</b>: Deletes a comment with the specified ID.
 * </ul>
 *
 * <p>Authorization Requirements:
 *
 * <ul>
 *   <li><b>comments:write</b> permission is required for creating and updating comments.
 *   <li><b>comments:read</b> permission is required for retrieving comments.
 *   <li><b>comments:delete</b> permission is required for deleting comments.
 * </ul>
 *
 * @see CommentRequest
 * @see CommentResponse
 * @see CommentService
 */
@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = CommentRestController.COMMENT_API_PATH)
public class CommentRestController {

  protected static final String COMMENT_API_PATH = "/api/v0/comments";
  private final CommentService commentService;

  /**
   * Creates a new comment.
   *
   * @param commentRequest the {@link CommentRequest} object containing details of the comment to
   *     create.
   * @return a {@link ResponseEntity} containing the created {@link CommentResponse} with HTTP
   *     status 201 (Created).
   */
  @PostMapping
  @PreAuthorize("hasAuthority('comments:write')")
  public ResponseEntity<CommentResponse> create(@Valid @RequestBody CommentRequest commentRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(commentService.create(commentRequest));
  }

  /**
   * Retrieves a paginated list of all comments.
   *
   * @param pageable the pagination information, with a default page size of 20.
   * @return a {@link ResponseEntity} containing a {@link Page} of {@link CommentResponse} objects
   *     with HTTP status 200 (OK).
   */
  @GetMapping
  @PreAuthorize("hasAuthority('comments:read')")
  public ResponseEntity<Page<CommentResponse>> getAll(@PageableDefault(20) Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(commentService.getAll(pageable));
  }

  /**
   * Retrieves a comment by its ID.
   *
   * @param id the ID of the comment to retrieve.
   * @return a {@link ResponseEntity} containing the {@link CommentResponse} with HTTP status 200
   *     (OK).
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('comments:read')")
  public ResponseEntity<CommentResponse> getById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(commentService.getById(id));
  }

  /**
   * Updates an existing comment with the specified ID.
   *
   * @param id the ID of the comment to update.
   * @param commentRequest the {@link CommentRequest} object containing updated details of the
   *     comment.
   * @return a {@link ResponseEntity} containing the updated {@link CommentResponse} with HTTP
   *     status 200 (OK).
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('comments:write')")
  public ResponseEntity<CommentResponse> update(
      @PathVariable Long id, @Valid @RequestBody CommentRequest commentRequest) {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(commentService.update(id, commentRequest));
  }

  /**
   * Deletes a comment with the specified ID.
   *
   * @param id the ID of the comment to delete.
   * @return a {@link ResponseEntity} with HTTP status 204 (No Content) indicating successful
   *     deletion.
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('comments:delete')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    commentService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
