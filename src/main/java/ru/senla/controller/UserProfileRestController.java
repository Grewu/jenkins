package ru.senla.controller;

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
import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.service.api.UserProfileService;

/**
 * The UserProfileRestController class provides REST endpoints for managing user profiles.
 * This controller supports creating, retrieving, updating, and deleting user profiles,
 * as well as retrieving comments associated with a specific profile.
 * Each endpoint has specific authorization requirements and supports JSON responses.
 * The {@link UserProfileService} is used to handle the business logic related to user profile management.
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li><b>POST /api/v0/user_profiles</b>: Creates a new user profile.</li>
 *     <li><b>GET /api/v0/user_profiles</b>: Retrieves a paginated list of all user profiles.</li>
 *     <li><b>GET /api/v0/user_profiles/{id}</b>: Retrieves a specific user profile by its ID.</li>
 *     <li><b>GET /api/v0/user_profiles/{userProfileId}/comments</b>: Retrieves comments associated with a user profile by its ID.</li>
 *     <li><b>PUT /api/v0/user_profiles/{id}</b>: Updates an existing user profile with the specified ID.</li>
 *     <li><b>DELETE /api/v0/user_profiles/{id}</b>: Deletes the user profile with the specified ID.</li>
 * </ul>
 *
 * <p>Authorization requirements:</p>
 * <ul>
 *     <li><b>user_profile:read</b> permission is required to retrieve and view user profiles and comments.</li>
 *     <li><b>user_profile:write</b> permission is required to create and update user profiles.</li>
 *     <li><b>user_profile:delete</b> permission is required to delete user profiles.</li>
 *     <li><b>comments:read</b> permission is required to retrieve comments.</li>
 * </ul>
 *
 * @see UserProfileRequest
 * @see UserProfileResponse
 * @see CommentResponse
 * @see UserProfileService
 */
@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UserProfileRestController.USER_PROFILE_API_PATH)
public class UserProfileRestController {

    protected static final String USER_PROFILE_API_PATH = "/api/v0/user_profiles";
    private final UserProfileService userProfileService;

    /**
     * Creates a new user profile.
     *
     * @param userProfileRequest the {@link UserProfileRequest} object containing the user profile details.
     * @return a {@link ResponseEntity} with the created {@link UserProfileResponse} and HTTP status 201 (Created).
     */
    @PostMapping
    @PreAuthorize("hasAuthority('user_profile:write')")
    public ResponseEntity<UserProfileResponse> create(@RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.create(userProfileRequest));
    }

    /**
     * Retrieves comments associated with a user profile by its ID.
     *
     * @param userProfileId the ID of the user profile for which to retrieve comments.
     * @param pageable      pagination information, with a default page size of 20.
     * @return a {@link ResponseEntity} containing a paginated list of {@link CommentResponse} and HTTP status 200 (OK).
     */
    @GetMapping("/{userProfileId}/comments")
    @PreAuthorize("hasAuthority('user_profile:read') && hasAuthority('comments:read')")
    public ResponseEntity<Page<CommentResponse>> getCommentsByProfileId(@PathVariable Long userProfileId,
                                                                        @PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.getAllCommentsByProfileId(userProfileId, pageable));
    }

    /**
     * Retrieves a paginated list of all user profiles.
     *
     * @param pageable pagination information, with a default page size of 20.
     * @return a {@link ResponseEntity} containing a paginated list of {@link UserProfileResponse} and HTTP status 200 (OK).
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user_profile:read')")
    public ResponseEntity<Page<UserProfileResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.getAll(pageable));
    }

    /**
     * Retrieves a specific user profile by its ID.
     *
     * @param id the ID of the user profile to retrieve.
     * @return a {@link ResponseEntity} containing the {@link UserProfileResponse} and HTTP status 200 (OK).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user_profile:read')")
    public ResponseEntity<UserProfileResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.getById(id));
    }

    /**
     * Updates an existing user profile with the specified ID.
     *
     * @param id                 the ID of the user profile to update.
     * @param userProfileRequest the {@link UserProfileRequest} object containing the updated details.
     * @return a {@link ResponseEntity} containing the updated {@link UserProfileResponse} and HTTP status 200 (OK).
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user_profile:write')")
    public ResponseEntity<UserProfileResponse> update(@PathVariable Long id,
                                                      @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.update(id, userProfileRequest));
    }

    /**
     * Deletes the user profile with the specified ID.
     *
     * @param id the ID of the user profile to delete.
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user_profile:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
