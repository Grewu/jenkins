package controller;

import config.TestContainersConfiguration;
import data.DepartmentTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.api.DepartmentService;
import util.IntegrationTest;
import util.PostgresqlTestContainer;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestContainersConfiguration.class)
class DepartmentControllerTestIT extends PostgresqlTestContainer {

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private static final String URL = "/api/v0/departments";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(departmentController)
                .build();
    }

    @Test
    void createShouldReturnDepartmentResponse() throws Exception {
        // given
        var departmentRequest = DepartmentTestData.builder().build().buildDepartmentRequest();
        var expectedResponse = DepartmentTestData.builder().build().buildDepartmentResponse();

        doReturn(expectedResponse).when(departmentService).create(departmentRequest);

        var requestBuilder = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "DEVELOPERS"
                        }
                        """);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "name": "DEVELOPERS"
                                }
                                """)
                );
    }

    @Test
    void getAllShouldReturnListOfDepartmentResponses() throws Exception {
        // given
        var expectedResponses = List.of(
                DepartmentTestData.builder().build().buildDepartmentResponse(),
                DepartmentTestData.builder().withId(2L).build().buildDepartmentResponse()
        );

        doReturn(expectedResponses).when(departmentService).getAll();

        // when
        mockMvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                  {
                                    "id": 1,
                                    "name": "DEVELOPERS"
                                  },
                                  {
                                    "id": 2,
                                    "name": "DEVELOPERS"
                                  }
                                ]
                                """)
                );
    }

    @Test
    void getByIdShouldReturnDepartmentResponse() throws Exception {
        // given
        var departmentResponse = DepartmentTestData.builder().build().buildDepartmentResponse();
        var departmentId = departmentResponse.id();

        doReturn(departmentResponse).when(departmentService).getById(departmentId);

        // when
        mockMvc.perform(get(URL_WITH_PARAMETER_ID, departmentId)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "name": "DEVELOPERS"
                                }
                                """)
                );
    }

//    @Test
//    void updateShouldReturnUpdatedDepartmentResponse() throws Exception {
//        // given
//        var departmentRequest = DepartmentTestData.builder()
//                .withName(DepartmentType.DEVELOPERS)
//                .build().buildDepartmentRequest();
//
//        var updatedResponse = DepartmentTestData.builder()
//                .withName(DepartmentType.DEVELOPERS)
//                .build().buildDepartmentResponse();
//
//        doReturn(updatedResponse).when(departmentService).update(1L, departmentRequest);
//
//        var requestBuilder = put(URL_WITH_PARAMETER_ID, 1L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("""
//                        {
//                            "name": "DEVELOPERS"
//                        }
//                        """);
//
//        // when
//        mockMvc.perform(requestBuilder)
//                // then
//                .andExpectAll(
//                        status().isOk(),
//                        content().contentType(MediaType.APPLICATION_JSON),
//                        content().json("""
//                                {
//                                    "id": 1,
//                                    "name": "DEVELOPERS"
//                                }
//                                """)
//                );
//    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        // given
        var departmentRequest = DepartmentTestData.builder().build().buildDepartmentRequest();

        // when
        mockMvc.perform(delete(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "DEVELOPERS"
                                }
                                """))
                // then
                .andExpect(status().isNoContent());

        verify(departmentService).delete(departmentRequest);
    }
}
