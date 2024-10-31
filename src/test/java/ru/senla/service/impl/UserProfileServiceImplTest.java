package ru.senla.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.senla.data.CommentTestData;
import ru.senla.data.DepartmentTestData;
import ru.senla.data.PositionTestData;
import ru.senla.data.UserProfileTestData;
import ru.senla.data.UserTestData;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.CommentMapper;
import ru.senla.mapper.UserProfileMapper;
import ru.senla.repository.api.CommentRepository;
import ru.senla.repository.api.DepartmentRepository;
import ru.senla.repository.api.PositionRepository;
import ru.senla.repository.api.UserProfileRepository;
import ru.senla.repository.api.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

  @Mock private UserProfileMapper userProfileMapper;

  @Mock private CommentMapper commentMapper;

  @Mock private UserProfileRepository userProfileRepository;

  @Mock private UserRepository userRepository;

  @Mock private DepartmentRepository departmentRepository;

  @Mock private CommentRepository commentRepository;

  @Mock private PositionRepository positionRepository;

  @InjectMocks private UserProfileServiceImpl userProfileService;

  private static final String ERROR_MESSAGE = "UserProfile with ID -1 was not found";

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
    void getAllShouldReturnCommentsByProfileId() {
      // given
      var pageable = Pageable.ofSize(2);
      var commentId = CommentTestData.builder().build().buildComment().getId();
      var comments = List.of(CommentTestData.builder().build().buildComment());
      var expectedResponses = List.of(CommentTestData.builder().build().buildCommentResponse());

      var commentPage = new PageImpl<>(comments, pageable, 2);

      doReturn(commentPage).when(commentRepository).findCommentsByProfileId(commentId, pageable);

      IntStream.range(0, comments.size())
          .forEach(
              i ->
                  doReturn(expectedResponses.get(i))
                      .when(commentMapper)
                      .toCommentResponse(comments.get(i)));

      // when
      var actualResponses =
          userProfileService.getAllCommentsByProfileId(commentId, pageable).getContent();

      // then
      assertEquals(expectedResponses, actualResponses);
    }

    @Test
    void getAllShouldReturnListOfUserProfileResponse() {
      // given
      var pageable = Pageable.ofSize(2);
      var userProfiles = List.of(UserProfileTestData.builder().build().buildUserProfile());
      var expectedResponses =
          List.of(UserProfileTestData.builder().build().buildUserProfileResponse());

      var userProfilePage = new PageImpl<>(userProfiles, pageable, 2);

      doReturn(userProfilePage).when(userProfileRepository).findAll(pageable);

      IntStream.range(0, userProfiles.size())
          .forEach(
              i ->
                  doReturn(expectedResponses.get(i))
                      .when(userProfileMapper)
                      .toUserProfileResponse(userProfiles.get(i)));

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

      when(userProfileRepository.findById(userProfile.getId()))
          .thenReturn(Optional.of(userProfile));
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
      var exception =
          assertThrows(EntityNotFoundException.class, () -> userProfileService.getById(id));

      // then
      assertEquals(ERROR_MESSAGE, exception.getMessage());
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
      var user = UserTestData.builder().build().buildUser();
      var department = DepartmentTestData.builder().build().buildDepartment();
      var position = PositionTestData.builder().build().buildPosition();

      when(userProfileRepository.findById(userProfile.getId()))
          .thenReturn(Optional.of(userProfile));
      when(userRepository.findById(userProfileRequest.user())).thenReturn(Optional.of(user));
      when(departmentRepository.findById(userProfile.getId())).thenReturn(Optional.of(department));
      when(positionRepository.findById(userProfile.getId())).thenReturn(Optional.of(position));

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

      doNothing().when(userProfileRepository).deleteById(id);

      assertThatNoException().isThrownBy(() -> userProfileService.delete(id));
    }
  }
}
