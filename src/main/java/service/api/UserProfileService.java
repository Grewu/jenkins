package service.api;

import model.dto.request.UserProfileRequest;
import model.dto.response.UserProfileResponse;
import model.entity.UserProfile;
import service.AbstractService;

import java.util.List;

public interface UserProfileService extends AbstractService<Long, UserProfileRequest, UserProfileResponse> {
    UserProfileResponse findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(Long userProfileId);
    UserProfileResponse findUserProfilesWithUserDepartmentAndPositionJPQL(Long userProfileId);
    UserProfileResponse findUserProfilesWithUserDepartmentAndPositionEntityGraph(Long userProfileId);
}
