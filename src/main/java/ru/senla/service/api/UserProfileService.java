package ru.senla.service.api;

import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.service.AbstractService;

public interface UserProfileService extends AbstractService<Long, UserProfileRequest, UserProfileResponse> {
}
