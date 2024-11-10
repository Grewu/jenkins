package ru.senla.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
import ru.senla.data.DepartmentTestData;
import ru.senla.data.UserProfileTestData;
import ru.senla.exception.EntityAlreadyExistsException;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.DepartmentMapper;
import ru.senla.mapper.UserProfileMapper;
import ru.senla.repository.api.DepartmentRepository;
import ru.senla.repository.api.UserProfileRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {
  private static final String ERROR_MESSAGE = "Department with ID -1 was not found";
  private static final String ERROR_MESSAGE_ENTITY_EXIST =
      "Department with 'DEVELOPERS' already exists";
  @Mock private DepartmentMapper departmentMapper;
  @Mock private UserProfileMapper userProfileMapper;

  @Mock private DepartmentRepository departmentRepository;

  @Mock private UserProfileRepository userProfileRepository;

  @InjectMocks private DepartmentServiceImpl departmentService;

  @Nested
  class Create {
    @Test
    void createShouldReturnDepartmentResponse() {
      // given
      var departmentRequest = DepartmentTestData.builder().build().buildDepartmentRequest();
      var department = DepartmentTestData.builder().build().buildDepartment();
      var expectedResponse = DepartmentTestData.builder().build().buildDepartmentResponse();

      when(departmentMapper.toDepartment(departmentRequest)).thenReturn(department);
      when(departmentRepository.save(department)).thenReturn(department);
      when(departmentMapper.toDepartmentResponse(department)).thenReturn(expectedResponse);

      // when
      var actualResponse = departmentService.create(departmentRequest);

      // then
      verify(departmentRepository).save(department);
      verify(departmentMapper).toDepartment(departmentRequest);
      verify(departmentMapper).toDepartmentResponse(department);
      assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateShouldThrowEntityAlreadyExistsException() {
      // given
      var departmentRequest = DepartmentTestData.builder().build().buildDepartmentRequest();
      // when
      when(departmentRepository.existsByName(departmentRequest.name())).thenReturn(Boolean.TRUE);

      var exception =
          assertThrows(
              EntityAlreadyExistsException.class,
              () -> departmentService.create(departmentRequest));

      // then
      assertEquals(ERROR_MESSAGE_ENTITY_EXIST, exception.getMessage());
    }
  }

  @Nested
  class GetAll {
    @Test
    void getAllShouldReturnUsersProfileByDepartmentId() {
      // given
      var pageable = Pageable.ofSize(2);
      var departmentId = DepartmentTestData.builder().build().buildDepartment().getId();
      var userProfiles = List.of(UserProfileTestData.builder().build().buildUserProfile());
      var expectedResponses =
          List.of(UserProfileTestData.builder().build().buildUserProfileResponse());

      var userProfilePage = new PageImpl<>(userProfiles, pageable, 2);

      doReturn(userProfilePage)
          .when(userProfileRepository)
          .findByDepartmentId(departmentId, pageable);

      IntStream.range(0, userProfiles.size())
          .forEach(
              i ->
                  doReturn(expectedResponses.get(i))
                      .when(userProfileMapper)
                      .toUserProfileResponse(userProfiles.get(i)));

      // when
      var actualResponses =
          departmentService.getAllUsersProfileByDepartmentId(departmentId, pageable).getContent();

      // then
      assertEquals(expectedResponses, actualResponses);
    }

    @Test
    void getAllShouldReturnListOfDepartmentResponses() {
      // given
      var pageable = Pageable.ofSize(2);
      var departments = List.of(DepartmentTestData.builder().build().buildDepartment());
      var expectedResponses =
          List.of(DepartmentTestData.builder().build().buildDepartmentResponse());

      var departmentPage = new PageImpl<>(departments, pageable, 2);

      doReturn(departmentPage).when(departmentRepository).findAll(pageable);

      IntStream.range(0, departments.size())
          .forEach(
              i ->
                  doReturn(expectedResponses.get(i))
                      .when(departmentMapper)
                      .toDepartmentResponse(departments.get(i)));

      // when
      var actualResponses = departmentService.getAll(pageable).getContent();

      // then
      assertEquals(expectedResponses, actualResponses);
    }
  }

  @Nested
  class GetById {
    @Test
    void getByIdShouldReturnExpectedDepartmentResponse() {
      // given
      var department = DepartmentTestData.builder().build().buildDepartment();
      var expectedResponse = DepartmentTestData.builder().build().buildDepartmentResponse();

      when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
      when(departmentMapper.toDepartmentResponse(department)).thenReturn(expectedResponse);

      // when
      var actualResponse = departmentService.getById(department.getId());

      // then
      assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getByIdShouldThrowNotFoundException() {
      // given
      var id = -1L;
      // when
      var exception =
          assertThrows(EntityNotFoundException.class, () -> departmentService.getById(id));

      // then
      assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
  }

  @Nested
  class Update {
    @Test
    void updateShouldReturnDepartmentResponse() {
      // given
      var departmentRequest = DepartmentTestData.builder().build().buildDepartmentRequest();
      var expectedResponse = DepartmentTestData.builder().build().buildDepartmentResponse();
      var department = DepartmentTestData.builder().build().buildDepartment();

      when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
      when(departmentMapper.update(departmentRequest, department)).thenReturn(department);
      when(departmentRepository.save(department)).thenReturn(department);
      when(departmentMapper.toDepartmentResponse(department)).thenReturn(expectedResponse);

      // when
      var actualResponse = departmentService.update(1L, departmentRequest);

      // then
      assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateShouldThrowEntityAlreadyExistsException() {
      // given
      var id = DepartmentTestData.builder().build().buildDepartment().getId();
      var departmentRequest = DepartmentTestData.builder().build().buildDepartmentRequest();
      // when
      when(departmentRepository.existsByName(departmentRequest.name())).thenReturn(Boolean.TRUE);

      var exception =
          assertThrows(
              EntityAlreadyExistsException.class,
              () -> departmentService.update(id, departmentRequest));

      // then
      assertEquals(ERROR_MESSAGE_ENTITY_EXIST, exception.getMessage());
    }
  }

  @Nested
  class Delete {
    @Test
    void deleteShouldCallDaoDeleteMethodThrowEntityNotFoundException() {
      // given
      var id = DepartmentTestData.builder().build().buildDepartment().getId();
      var department = DepartmentTestData.builder().build().buildDepartment();
      // when
      when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
      // then
      assertThatThrownBy(() -> departmentService.delete(id))
          .isInstanceOf(EntityNotFoundException.class);
    }
  }
}
