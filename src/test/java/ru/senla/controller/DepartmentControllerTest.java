package ru.senla.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.senla.data.DepartmentTestData;
import ru.senla.data.UserProfileTestData;
import ru.senla.model.entity.enums.DepartmentType;
import ru.senla.service.api.DepartmentService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private static final String URL = "/api/v0/departments";
    private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";
    private static final String URL_WITH_DEPARTMENT_ID_USERS = URL + "/{departmentId}/users";

    @Nested
    class Create {
        @ParameterizedTest
        @CsvSource({
                "DEVELOPERS, 1",
                "DESIGN, 2",
                "FINANCE, 3"
        })
        @WithMockUser(authorities = {"department:write"})
        void createShouldReturnDepartmentResponse(String name, Long id) throws Exception {
            // given
            var departmentRequest = DepartmentTestData.builder()
                    .withName(DepartmentType.valueOf(name))
                    .build().buildDepartmentRequest();
            var expectedResponse = DepartmentTestData.builder()
                    .withId(id)
                    .withName(DepartmentType.valueOf(name))
                    .build().buildDepartmentResponse();

            doReturn(expectedResponse)
                    .when(departmentService).create(departmentRequest);

            var requestBuilder = post(URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                                "name": "%s"
                            }
                            """.formatted(name));

            // when
            mockMvc.perform(requestBuilder)
                    // then
                    .andExpect(status().isCreated());

            verify(departmentService).create(departmentRequest);
        }


        @Test
        @WithMockUser(authorities = {"department:write"})
        void createShouldReturnDepartmentResponse() throws Exception {
            // given
            var departmentRequest = DepartmentTestData.builder().build().buildDepartmentRequest();
            var expectedResponse = DepartmentTestData.builder().build().buildDepartmentResponse();

            doReturn(expectedResponse)
                    .when(departmentService).create(departmentRequest);

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

            verify(departmentService).create(any());
        }

        @Test
        void createShouldReturnForbidden() throws Exception {
            // given
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
                    .andExpect(status().isForbidden());

            verify(departmentService, never()).create(any());
        }
    }

    @Nested
    class GetAll {
        @Test
        @WithMockUser(authorities = {"department:read", "user_profile:read"})
        void getAllShouldReturnListOfUsersProfilesResponseByDepartmentId() throws Exception {
            // given
            var pageable = Pageable.ofSize(2);
            var departmentId = DepartmentTestData.builder().build().buildDepartment().getId();

            var expectedResponses = List.of(
                    UserProfileTestData.builder().build().buildUserProfileResponse(),
                    UserProfileTestData.builder().withId(2L).build().buildUserProfileResponse()
            );

            when(departmentService.getAllUsersProfileByDepartmentId(anyLong(), any(Pageable.class)))
                    .thenReturn(new PageImpl<>(expectedResponses, pageable, 2));

            // when
            mockMvc.perform(get(URL_WITH_DEPARTMENT_ID_USERS, departmentId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content.size()").value(expectedResponses.size()))
                    .andExpect(jsonPath("$.content[0].id").value(expectedResponses.get(0).id()))
                    .andExpect(jsonPath("$.content[0].firstName").value(expectedResponses.get(0).firstName()))
                    .andExpect(jsonPath("$.content[0].lastName").value(expectedResponses.get(0).lastName()))
                    .andExpect(jsonPath("$.content[0].position").value(expectedResponses.get(0).position()))
                    .andExpect(jsonPath("$.content[0].department").value(expectedResponses.get(0).department()))
                    .andExpect(jsonPath("$.content[0].user").value(expectedResponses.get(0).user()))
                    .andExpect(jsonPath("$.content[1].id").value(expectedResponses.get(1).id()))
                    .andExpect(jsonPath("$.content[1].firstName").value(expectedResponses.get(1).firstName()))
                    .andExpect(jsonPath("$.content[1].lastName").value(expectedResponses.get(1).lastName()))
                    .andExpect(jsonPath("$.content[1].position").value(expectedResponses.get(1).position()))
                    .andExpect(jsonPath("$.content[1].department").value(expectedResponses.get(1).department()))
                    .andExpect(jsonPath("$.content[1].user").value(expectedResponses.get(1).user()));
        }


        @Test
        @WithMockUser(authorities = {"department:read"})
        void getAllShouldReturnListOfDepartmentResponses() throws Exception {
            // given
            var pageable = Pageable.ofSize(2);

            var expectedResponses = List.of(
                    DepartmentTestData.builder().build().buildDepartmentResponse(),
                    DepartmentTestData.builder().withId(2L).build().buildDepartmentResponse()
            );

            when(departmentService.getAll(any(Pageable.class)))
                    .thenReturn(new PageImpl<>(expectedResponses, pageable, 2));

            // when
            mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpectAll(
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$.content").isNotEmpty())
                    .andExpect(jsonPath("$.content.size()").value(expectedResponses.size()))
                    .andExpect(jsonPath("$.content[0].id").value(expectedResponses.get(0).id()))
                    .andExpect(jsonPath("$.content[0].name").value(expectedResponses.get(0).name()))
                    .andExpect(jsonPath("$.content[1].id").value(expectedResponses.get(1).id()))
                    .andExpect(jsonPath("$.content[1].name").value(expectedResponses.get(1).name()));
        }

        @Test
        void getAllShouldReturnForbidden() throws Exception {
            //given
            var pageable = Pageable.ofSize(2);
            // when
            mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isForbidden());

            verify(departmentService, never()).getAll(pageable);
        }
    }

    @Nested
    class GetByID {
        @ParameterizedTest
        @CsvSource({
                "1, DEVELOPERS",
                "2, MANAGERS",
                "3, MARKETING"
        })
        @WithMockUser(authorities = {"department:read"})
        void getByIdShouldReturnDepartmentResponse(Long id, String expectedName) throws Exception {
            // given
            var expectedResponse = DepartmentTestData.builder()
                    .withId(id)
                    .withName(DepartmentType.valueOf(expectedName))
                    .build().buildDepartmentResponse();

            doReturn(expectedResponse)
                    .when(departmentService).getById(id);

            // when
            mockMvc.perform(get(URL_WITH_PARAMETER_ID, id)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isOk());

            verify(departmentService).getById(id);
        }

        @Test
        @WithMockUser(authorities = {"department:read"})
        void getByIdShouldReturnDepartmentResponse() throws Exception {
            // given
            var departmentResponse = DepartmentTestData.builder().build().buildDepartmentResponse();
            var departmentId = departmentResponse.id();

            doReturn(departmentResponse)
                    .when(departmentService).getById(departmentId);

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
            verify(departmentService).getById(any());
        }

        @Test
        void getByIdShouldReturnForbidden() throws Exception {
            // given
            var departmentResponse = DepartmentTestData.builder().build().buildDepartmentResponse();
            var departmentId = departmentResponse.id();

            doReturn(departmentResponse)
                    .when(departmentService).getById(departmentId);

            // when
            mockMvc.perform(get(URL_WITH_PARAMETER_ID, departmentId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isForbidden());

            verify(departmentService, never()).getById(any());
        }
    }

    @Nested
    class Update {
        @Test
        @WithMockUser(authorities = {"department:write"})
        void updateShouldReturnUpdatedDepartmentResponse() throws Exception {
            // given
            var departmentId = 1L;
            var departmentRequest = DepartmentTestData.builder()
                    .withName(DepartmentType.DEVELOPERS)
                    .build().buildDepartmentRequest();

            var updatedResponse = DepartmentTestData.builder()
                    .withId(departmentId)
                    .withName(DepartmentType.DEVELOPERS)
                    .build().buildDepartmentResponse();

            doReturn(updatedResponse)
                    .when(departmentService).update(departmentId, departmentRequest);

            var requestBuilder = put(URL_WITH_PARAMETER_ID, departmentId)
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
                            status().isOk(),
                            content().contentType(MediaType.APPLICATION_JSON),
                            content().json("""
                                    {
                                         "id": 1,
                                         "name": "DEVELOPERS"
                                    }
                                    """)
                    );

            verify(departmentService).update(any(), any());
        }

        @Test
        void updateShouldReturnForbidden() throws Exception {
            // given
            var departmentId = 1L;

            // when
            mockMvc.perform(put(URL_WITH_PARAMETER_ID, departmentId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "name": "Updated Department"
                                    }
                                    """))
                    // then
                    .andExpect(status().isForbidden());

            verify(departmentService, never()).update(any(), any());
        }
    }

    @Nested
    class Delete {
        @Test
        @WithMockUser(authorities = {"department:delete"})
        void deleteShouldReturnNoContent() throws Exception {
            // given
            var departmentId = 1L;

            // when
            mockMvc.perform(delete(URL_WITH_PARAMETER_ID, departmentId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isNoContent());

            verify(departmentService).delete(departmentId);
        }

        @Test
        void deleteShouldReturnForbidden() throws Exception {
            // given
            var departmentId = 1L;

            // when
            mockMvc.perform(delete(URL_WITH_PARAMETER_ID, departmentId)
                            .contentType(MediaType.APPLICATION_JSON))
                    // then
                    .andExpect(status().isForbidden());

            verify(departmentService, never()).delete(departmentId);
        }
    }
}
