package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
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
        var currentUserProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));


        var user = userRepository.findById(userProfileRequest.user())
                .orElseThrow(() -> new EntityNotFoundException(User.class, id));

        var department = departmentRepository.findById(userProfileRequest.department())
                .orElseThrow(() -> new EntityNotFoundException(Department.class, id));

        var position = positionRepository.findById(userProfileRequest.position())
                .orElseThrow(() -> new EntityNotFoundException(Position.class, id));

        currentUserProfile.setDepartment(department);
        currentUserProfile.setUser(user);
        currentUserProfile.setPosition(position);

        return userProfileMapper.toUserProfileResponse(userProfileRepository.save(currentUserProfile));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userProfileRepository.deleteById(id);
    }


    @Override
    public Page<CommentResponse> getAllCommentsByProfileId(Long userProfileId, Pageable pageable) {
        return commentRepository.findCommentsByProfileId(userProfileId,pageable)
                .map(commentMapper::toCommentResponse);
    }
}
