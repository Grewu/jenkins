package ru.senla.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.service.AbstractService;

/**
 * Service interface for managing user profiles.
 * <p>
 * This interface extends {@link AbstractService} to provide basic CRUD operations
 * for user profiles, as well as additional functionalities related to user comments.
 * </p>
 */
public interface UserProfileService extends AbstractService<Long, UserProfileRequest, UserProfileResponse> {
    /**
     * Retrieves a paginated list of comments associated with a specific user profile.
     *
     * @param userProfileId the ID of the user profile for which comments are to be retrieved
     * @param pageable      pagination information, including page number and size
     * @return a {@link Page} of {@link CommentResponse} containing the comments for the specified user profile
     */
    Page<CommentResponse> getAllCommentsByProfileId(Long userProfileId, Pageable pageable);
}
