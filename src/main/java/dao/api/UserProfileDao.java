package dao.api;

import dao.AbstractDao;
import model.entity.UserProfile;

import java.util.List;

public interface UserProfileDao extends AbstractDao<Long, UserProfile> {

    UserProfile findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(Long userProfileId);

    UserProfile findUserProfilesWithUserDepartmentAndPositionJPQL(Long userProfileId);

    UserProfile findUserProfilesWithUserDepartmentAndPositionEntityGraph(Long userProfileId);
}
