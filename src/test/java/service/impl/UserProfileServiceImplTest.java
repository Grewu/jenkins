package service.impl;

import dao.api.UserProfileDao;
import data.UserProfileTestData;
import exception.EntityNotFoundException;
import mapper.UserProfileMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileMapper mapper;

    @Mock
    private UserProfileDao userProfileDao;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Nested
    class Create {
        @Test
        void createShouldReturnUserProfile() {
            // given
            var userProfileRequest = UserProfileTestData.builder().build().buildUserProfileRequest();
            var userProfile = UserProfileTestData.builder().build().buildUserProfile();
            var expected = UserProfileTestData.builder().build().buildUserProfileResponse();

            when(mapper.toUserProfile(userProfileRequest)).thenReturn(userProfile);
            when(mapper.toUserProfileResponse(userProfile)).thenReturn(expected);
            when(userProfileDao.create(userProfile)).thenReturn(userProfile);
            // when
            var actual = userProfileService.create(userProfileRequest);

            // then
            verify(userProfileDao).create(userProfile);
            verify(mapper).toUserProfile(userProfileRequest);
            verify(mapper).toUserProfileResponse(userProfile);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class GetAll {
        @Test
        void getAllShouldReturnListOfUserProfileResponse() {
            // given
            var userProfiles = List.of(UserProfileTestData.builder().build().buildUserProfile());
            var expectedResponses = List.of(UserProfileTestData.builder().build().buildUserProfileResponse());

            when(userProfileDao.findAll()).thenReturn(userProfiles);
            when(mapper.toUserProfileResponse(userProfiles.get(0))).thenReturn(expectedResponses.get(0));
            // when
            var actualResponses = userProfileService.getAll();

            // then
            verify(userProfileDao).findAll();
            verify(mapper).toUserProfileResponse(userProfiles.get(0));
            assertEquals(expectedResponses, actualResponses);
        }
    }

    @Nested
    class GetById {
        @Test
        void getByIdShouldReturnExpectedUserProfile() {
            // given
            var userProfile = UserProfileTestData.builder().build().buildUserProfile();
            var expected = UserProfileTestData.builder().build().buildUserProfileResponse();

            when(userProfileDao.findById(userProfile.getId())).thenReturn(Optional.of(userProfile));
            when(mapper.toUserProfileResponse(userProfile)).thenReturn(expected);
            // when
            var actual = userProfileService.getById(userProfile.getId());
            // then
            assertEquals(expected, actual);
        }

        @Test
        void getByIdShouldThrowNotFoundException() {
            // given
            var id = -1L;
            var expected = "UserProfile with ID -1 was not found";

            // when
            var exception = assertThrows(EntityNotFoundException.class,
                    () -> userProfileService.getById(id));

            // then
            assertEquals(expected, exception.getMessage());
        }
    }

    @Nested
    class Update {
        @Test
        void updateShouldReturnUserProfileResponse() {
            // given
            var userProfileRequest = UserProfileTestData.builder().build().buildUserProfileRequest();
            var expected = UserProfileTestData.builder().build().buildUserProfileResponse();
            var userProfile = UserProfileTestData.builder().build().buildUserProfile();

            when(mapper.toUserProfile(userProfileRequest)).thenReturn(userProfile);
            when(userProfileDao.update(userProfile)).thenReturn(userProfile);
            when(mapper.toUserProfileResponse(userProfile)).thenReturn(expected);

            // when
            var actual = userProfileService.update(1L, userProfileRequest);

            // then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class Delete {
        @Test
        void deleteShouldCallDaoDeleteMethod() {
            // given
            var userProfileRequest = UserProfileTestData.builder().build().buildUserProfileRequest();
            var userProfile = UserProfileTestData.builder().build().buildUserProfile();

            when(mapper.toUserProfile(userProfileRequest)).thenReturn(userProfile);

            // when
            userProfileService.delete(userProfileRequest);

            // then
            verify(userProfileDao).delete(userProfile);
        }
    }

    @Nested
    class FindWithDepartmentAndPosition {
        @Test
        void findUserProfilesWithUserDepartmentAndPositionCriteriaAPIShouldReturnUserProfileResponse() {
            // given
            var userProfile = UserProfileTestData.builder().build().buildUserProfile();
            var expected = UserProfileTestData.builder().build().buildUserProfileResponse();

            when(userProfileDao.findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(userProfile.getId()))
                    .thenReturn(userProfile);
            when(mapper.toUserProfileResponse(userProfile)).thenReturn(expected);

            // when
            var actual = userProfileService
                    .findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(userProfile.getId());

            // then
            verify(userProfileDao).findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(userProfile.getId());
            assertEquals(expected, actual);
        }

        @Test
        void findUserProfilesWithUserDepartmentAndPositionJPQLShouldReturnUserProfileResponse() {
            // given
            var userProfile = UserProfileTestData.builder().build().buildUserProfile();
            var expected = UserProfileTestData.builder().build().buildUserProfileResponse();

            when(userProfileDao.findUserProfilesWithUserDepartmentAndPositionJPQL(userProfile.getId()))
                    .thenReturn(userProfile);
            when(mapper.toUserProfileResponse(userProfile)).thenReturn(expected);

            // when
            var actual = userProfileService
                    .findUserProfilesWithUserDepartmentAndPositionJPQL(userProfile.getId());

            // then
            verify(userProfileDao).findUserProfilesWithUserDepartmentAndPositionJPQL(userProfile.getId());
            assertEquals(expected, actual);
        }

        @Test
        void findUserProfilesWithUserDepartmentAndPositionEntityGraphShouldReturnUserProfileResponse() {
            // given
            var userProfile = UserProfileTestData.builder().build().buildUserProfile();
            var expected = UserProfileTestData.builder().build().buildUserProfileResponse();

            when(userProfileDao.findUserProfilesWithUserDepartmentAndPositionEntityGraph(userProfile.getId()))
                    .thenReturn(userProfile);
            when(mapper.toUserProfileResponse(userProfile)).thenReturn(expected);

            // when
            var actual = userProfileService
                    .findUserProfilesWithUserDepartmentAndPositionEntityGraph(userProfile.getId());

            // then
            verify(userProfileDao).findUserProfilesWithUserDepartmentAndPositionEntityGraph(userProfile.getId());
            assertEquals(expected, actual);
        }
    }

}
