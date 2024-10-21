package ru.senla.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.service.AbstractService;

public interface UserProfileService extends AbstractService<Long, UserProfileRequest, UserProfileResponse> {
    Page<CommentResponse> getAllCommentsByProfileId(Long userProfileId, Pageable pageable);
}
