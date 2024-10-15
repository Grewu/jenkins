package ru.senla.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.senla.data.UserProfileTestData;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.UserProfileMapper;
import ru.senla.repository.api.UserProfileRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileMapper userProfileMapper;

    @Mock
    private UserProfileRepository userProfileRepository;

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

            when(userProfileMapper.toUserProfile(userProfileRequest)).thenReturn(userProfile);
            when(userProfileMapper.toUserProfileResponse(userProfile)).thenReturn(expected);
            when(userProfileRepository.save(userProfile)).thenReturn(userProfile);
            // when
            var actual = userProfileService.create(userProfileRequest);

            // then
            verify(userProfileRepository).save(userProfile);
            verify(userProfileMapper).toUserProfile(userProfileRequest);
            verify(userProfileMapper).toUserProfileResponse(userProfile);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class GetAll {
        @Test
        void getAllShouldReturnListOfUserProfileResponse() {
            // given
            var pageable = Pageable.ofSize(2);
            var userProfiles = List.of(UserProfileTestData.builder().build().buildUserProfile());
            var expectedResponses = List.of(UserProfileTestData.builder().build().buildUserProfileResponse());

            var userProfilePage = new PageImpl<>(userProfiles,pageable,2);

            doReturn(userProfilePage)
                    .when(userProfileRepository).findAll(pageable);

            IntStream.range(0, userProfiles.size())
                    .forEach(i -> doReturn(expectedResponses.get(i))
                            .when(userProfileMapper).toUserProfileResponse(userProfiles.get(i)));

            // when
            var actualResponses = userProfileService.getAll(pageable).getContent();

            // then
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

            when(userProfileRepository.findById(userProfile.getId())).thenReturn(Optional.of(userProfile));
            when(userProfileMapper.toUserProfileResponse(userProfile)).thenReturn(expected);
            // when
            var actual = userProfileService.getById(userProfile.getId());
            // then
            assertEquals(expected, actual);
        }

        @Test
        void getByIdShouldThrowNotFoundException() {
            // given
            var id = -1L;
            // when
            var exception = assertThrows(EntityNotFoundException.class,
                    () -> userProfileService.getById(id));

            // then
            assertEquals("UserProfile with ID -1 was not found", exception.getMessage());
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

            when(userProfileRepository.findById(userProfile.getId())).thenReturn(Optional.of(userProfile));
            when(userProfileMapper.update(userProfileRequest, userProfile)).thenReturn(userProfile);
            when(userProfileRepository.save(userProfile)).thenReturn(userProfile);
            when(userProfileMapper.toUserProfileResponse(userProfile)).thenReturn(expected);
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
            var id = 1L;

            doNothing()
                    .when(userProfileRepository).deleteById(id);

            assertThatNoException()
                    .isThrownBy(() -> userProfileService.delete(id));
        }
    }

}
