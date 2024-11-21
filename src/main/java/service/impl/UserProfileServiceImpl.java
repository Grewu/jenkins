package service.impl;

import dao.api.UserProfileDao;
import exception.EntityNotFoundException;
import mapper.UserProfileMapper;
import model.dto.request.UserProfileRequest;
import model.dto.response.UserProfileResponse;
import model.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.api.UserProfileService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileDao userProfileDao;
    private final UserProfileMapper userProfileMapper;

    @Autowired
    public UserProfileServiceImpl(UserProfileDao employeeDao, UserProfileMapper userProfileMapper) {
        this.userProfileDao = employeeDao;
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    @Transactional
    public UserProfileResponse create(UserProfileRequest userProfileRequest) {
        var userProfile = userProfileMapper.toUserProfile(userProfileRequest);
        return userProfileMapper.toUserProfileResponse(userProfileDao.create(userProfile));
    }

    @Override
    public List<UserProfileResponse> getAll() {
        return userProfileDao.findAll().stream()
                .map(userProfileMapper::toUserProfileResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserProfileResponse getById(Long id) {
        return userProfileDao.findById(id)
                .map(userProfileMapper::toUserProfileResponse)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));
    }

    @Override
    @Transactional
    public UserProfileResponse update(Long id, UserProfileRequest userProfileRequest) {
        var employee = userProfileMapper.toUserProfile(userProfileRequest);
        return userProfileMapper.toUserProfileResponse(userProfileDao.update(employee));
    }

    @Override
    @Transactional
    public void delete(UserProfileRequest userProfileRequest) {
        var employee = userProfileMapper.toUserProfile(userProfileRequest);
        userProfileDao.delete(employee);
    }

    @Override
    public UserProfileResponse findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(Long userProfileId) {
        return userProfileMapper.toUserProfileResponse(
                userProfileDao.findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(userProfileId));
    }

    @Override
    public UserProfileResponse findUserProfilesWithUserDepartmentAndPositionJPQL(Long userProfileId) {
        return userProfileMapper.toUserProfileResponse(
                userProfileDao.findUserProfilesWithUserDepartmentAndPositionJPQL(userProfileId));
    }

    @Override
    public UserProfileResponse findUserProfilesWithUserDepartmentAndPositionEntityGraph(Long userProfileId) {
        return userProfileMapper.toUserProfileResponse(
                userProfileDao.findUserProfilesWithUserDepartmentAndPositionEntityGraph(userProfileId));
    }
}
