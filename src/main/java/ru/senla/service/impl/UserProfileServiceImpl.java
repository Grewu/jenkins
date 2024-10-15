package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.UserProfileMapper;
import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.model.entity.UserProfile;
import ru.senla.repository.api.UserProfileRepository;
import ru.senla.service.api.UserProfileService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    @Transactional
    public UserProfileResponse create(UserProfileRequest userProfileRequest) {
        var userProfile = userProfileMapper.toUserProfile(userProfileRequest);
        return userProfileMapper.toUserProfileResponse(userProfileRepository.save(userProfile));
    }

    @Override
    public Page<UserProfileResponse> getAll(Pageable pageable) {
        return userProfileRepository.findAll(pageable)
                .map(userProfileMapper::toUserProfileResponse);
    }

    @Override
    public UserProfileResponse getById(Long id) {
        return userProfileRepository.findById(id)
                .map(userProfileMapper::toUserProfileResponse)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));
    }

    @Override
    @Transactional
    public UserProfileResponse update(Long id, UserProfileRequest userProfileRequest) {
        return userProfileRepository.findById(id)
                .map(current -> userProfileMapper.update(userProfileRequest, current))
                .map(userProfileRepository::save)
                .map(userProfileMapper::toUserProfileResponse)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userProfileRepository.deleteById(id);
    }


}
