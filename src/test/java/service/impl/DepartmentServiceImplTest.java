package service.impl;

import dao.api.DepartmentDao;
import data.DepartmentTestData;
import exception.EntityNotFoundException;
import mapper.DepartmentMapper;
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
class DepartmentServiceImplTest {

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private DepartmentDao departmentDao;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Nested
    class Create {
        @Test
        void createShouldReturnDepartmentResponse() {
            // given
            var departmentRequest = DepartmentTestData.builder().build().buildDepartmentRequest();
            var department = DepartmentTestData.builder().build().buildDepartment();
            var expectedResponse = DepartmentTestData.builder().build().buildDepartmentResponse();

            when(departmentMapper.toDepartment(departmentRequest)).thenReturn(department);
            when(departmentDao.create(department)).thenReturn(department);
            when(departmentMapper.toDepartmentResponse(department)).thenReturn(expectedResponse);

            // when
            var actualResponse = departmentService.create(departmentRequest);

            // then
            verify(departmentDao).create(department);
            verify(departmentMapper).toDepartment(departmentRequest);
            verify(departmentMapper).toDepartmentResponse(department);
            assertEquals(expectedResponse, actualResponse);
        }
    }

    @Nested
    class GetAll {
        @Test
        void getAllShouldReturnListOfDepartmentResponses() {
            // given
            var departments = List.of(DepartmentTestData.builder().build().buildDepartment());
            var expectedResponses = List.of(DepartmentTestData.builder().build().buildDepartmentResponse());

            when(departmentDao.findAll()).thenReturn(departments);
            when(departmentMapper.toDepartmentResponse(departments.get(0))).thenReturn(expectedResponses.get(0));

            // when
            var actualResponses = departmentService.getAll();

            // then
            verify(departmentDao).findAll();
            verify(departmentMapper).toDepartmentResponse(departments.get(0));
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

            when(departmentDao.findById(department.getId())).thenReturn(Optional.of(department));
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
            var expectedMessage = "Department with ID -1 was not found";

            // when
            var exception = assertThrows(EntityNotFoundException.class,
                    () -> departmentService.getById(id));

            // then
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

//    @Nested
//    class Update {
//        @Test
//        void updateShouldReturnDepartmentResponse() {
//            // given
//            var departmentRequest = DepartmentTestData.builder().build().buildDepartmentRequest();
//            var expectedResponse = DepartmentTestData.builder().build().buildDepartmentResponse();
//            var department = DepartmentTestData.builder().build().buildDepartment();
//
//            when(departmentMapper.toDepartment(departmentRequest)).thenReturn(department);
//            when(departmentDao.update(department)).thenReturn(department);
//            when(departmentMapper.toDepartmentResponse(department)).thenReturn(expectedResponse);
//
//            // when
//            var actualResponse = departmentService.update(1L, departmentRequest);
//
//            // then
//            assertEquals(expectedResponse, actualResponse);
//        }
//    }

    @Nested
    class Delete {
        @Test
        void deleteShouldCallDaoDeleteMethod() {
            // given
            var departmentRequest = DepartmentTestData.builder().build().buildDepartmentRequest();
            var department = DepartmentTestData.builder().build().buildDepartment();

            when(departmentMapper.toDepartment(departmentRequest)).thenReturn(department);

            // when
            departmentService.delete(departmentRequest);

            // then
            verify(departmentDao).delete(department);
        }
    }
}
