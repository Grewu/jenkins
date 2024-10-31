package ru.senla.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
import ru.senla.data.PositionTestData;
import ru.senla.model.entity.enums.PositionType;
import ru.senla.service.api.PositionService;

@SpringBootTest
@AutoConfigureMockMvc
public class PositionControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private PositionService positionService;

  private static final String URL = "/api/v0/positions";
  private static final String URL_WITH_PARAMETER_ID = URL + "/{id}";

  @Nested
  class Create {
    @ParameterizedTest
    @CsvSource({"MANAGER, 1", "DEVELOPER, 2", "SALESPERSON, 3"})
    @WithMockUser(authorities = {"position:write"})
    void createShouldReturnPositionResponse(String name, Long id) throws Exception {
      // given
      var positionRequest =
          PositionTestData.builder()
              .withName(PositionType.valueOf(name))
              .build()
              .buildPositionRequest();
      var expectedResponse =
          PositionTestData.builder()
              .withId(id)
              .withName(PositionType.valueOf(name))
              .build()
              .buildPositionResponse();

      doReturn(expectedResponse).when(positionService).create(positionRequest);

      var requestBuilder =
          post(URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                                            {
                                                "name": "%s"
                                            }
                                            """
                      .formatted(name));

      // when
      mockMvc
          .perform(requestBuilder)
          // then
          .andExpect(status().isCreated());

      verify(positionService).create(positionRequest);
    }

    @Test
    @WithMockUser(authorities = {"position:write"})
    void createShouldReturnPositionResponse() throws Exception {
      // given
      var positionRequest = PositionTestData.builder().build().buildPositionRequest();
      var expectedResponse = PositionTestData.builder().build().buildPositionResponse();

      doReturn(expectedResponse).when(positionService).create(positionRequest);

      var requestBuilder =
          post(URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                                            {
                                                "name": "DEVELOPER"
                                            }
                                            """);

      // when
      mockMvc
          .perform(requestBuilder)
          // then
          .andExpectAll(
              status().isCreated(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      """
                                                     {
                                                         "id": 1,
                                                         "name": "DEVELOPER"
                                                     }
                                                    """));

      verify(positionService).create(any());
    }

    @Test
    void createShouldReturnForbidden() throws Exception {
      // given
      var requestBuilder =
          post(URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                                            {
                                                "name": "DEVELOPER"
                                            }
                                            """);

      // when
      mockMvc
          .perform(requestBuilder)
          // then
          .andExpect(status().isForbidden());

      verify(positionService, never()).create(any());
    }
  }

  @Nested
  class GetAll {

    @Test
    @WithMockUser(authorities = {"position:read"})
    void getAllShouldReturnListOfPositionResponses() throws Exception {
      // given
      var pageable = Pageable.ofSize(2);

      var expectedResponses =
          List.of(
              PositionTestData.builder().build().buildPositionResponse(),
              PositionTestData.builder().withId(2L).build().buildPositionResponse());

      var expectedPage = new PageImpl<>(expectedResponses, pageable, expectedResponses.size());

      when(positionService.getAll(any(Pageable.class))).thenReturn(expectedPage);

      // when
      mockMvc
          .perform(get(URL).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(content().json(objectMapper.writeValueAsString(expectedPage)));
    }

    @Test
    void getAllShouldReturnForbidden() throws Exception {
      // given
      var pageable = Pageable.ofSize(2);
      // when
      mockMvc
          .perform(get(URL).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(positionService, never()).getAll(pageable);
    }
  }

  @Nested
  class GetByID {
    @ParameterizedTest
    @CsvSource({"1, MANAGER", "2, DEVELOPER", "3, SALESPERSON"})
    @WithMockUser(authorities = {"position:read"})
    void getByIdShouldReturnPositionResponse(Long id, String expectedName) throws Exception {
      // given
      var expectedResponse =
          PositionTestData.builder()
              .withId(id)
              .withName(PositionType.valueOf(expectedName))
              .build()
              .buildPositionResponse();

      doReturn(expectedResponse).when(positionService).getById(id);

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, id).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isOk());

      verify(positionService).getById(id);
    }

    @Test
    @WithMockUser(authorities = {"position:read"})
    void getByIdShouldReturnPositionResponse() throws Exception {
      // given
      var positionResponse = PositionTestData.builder().build().buildPositionResponse();
      var positionId = positionResponse.id();

      doReturn(positionResponse).when(positionService).getById(positionId);

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, positionId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      """
                                                    {
                                                       "id": 1,
                                                       "name": "DEVELOPER"
                                                    }
                                                    """));
      verify(positionService).getById(any());
    }

    @Test
    void getByIdShouldReturnForbidden() throws Exception {
      // given
      var positionResponse = PositionTestData.builder().build().buildPositionResponse();
      var positionId = positionResponse.id();

      doReturn(positionResponse).when(positionService).getById(positionId);

      // when
      mockMvc
          .perform(get(URL_WITH_PARAMETER_ID, positionId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(positionService, never()).getById(any());
    }
  }

  @Nested
  class Update {
    @Test
    @WithMockUser(authorities = {"position:write"})
    void updateShouldReturnUpdatedDepartmentResponse() throws Exception {
      // given
      var positionId = 1L;
      var positionRequest =
          PositionTestData.builder()
              .withName(PositionType.DEVELOPER)
              .build()
              .buildPositionRequest();

      var updatedResponse =
          PositionTestData.builder()
              .withId(positionId)
              .withName(PositionType.DEVELOPER)
              .build()
              .buildPositionResponse();

      doReturn(updatedResponse).when(positionService).update(positionId, positionRequest);

      var requestBuilder =
          put(URL_WITH_PARAMETER_ID, positionId)
              .contentType(MediaType.APPLICATION_JSON)
              .content(
                  """
                                            {
                                                "name": "DEVELOPER"
                                            }
                                            """);

      // when
      mockMvc
          .perform(requestBuilder)
          // then
          .andExpectAll(
              status().isOk(),
              content().contentType(MediaType.APPLICATION_JSON),
              content()
                  .json(
                      """
                                                    {
                                                         "id": 1,
                                                         "name": "DEVELOPER"
                                                    }
                                                    """));

      verify(positionService).update(any(), any());
    }

    @Test
    void updateShouldReturnForbidden() throws Exception {
      // given
      var positionId = 1L;

      // when
      mockMvc
          .perform(
              put(URL_WITH_PARAMETER_ID, positionId)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      """
                                                    {
                                                        "name": "Updated Department"
                                                    }
                                                    """))
          // then
          .andExpect(status().isForbidden());

      verify(positionService, never()).update(any(), any());
    }
  }

  @Nested
  class Delete {
    @Test
    @WithMockUser(authorities = {"position:delete"})
    void deleteShouldReturnNoContent() throws Exception {
      // given
      var positionId = 1L;

      // when
      mockMvc
          .perform(
              delete(URL_WITH_PARAMETER_ID, positionId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isNoContent());

      verify(positionService).delete(positionId);
    }

    @Test
    void deleteShouldReturnForbidden() throws Exception {
      // given
      var positionId = 1L;

      // when
      mockMvc
          .perform(
              delete(URL_WITH_PARAMETER_ID, positionId).contentType(MediaType.APPLICATION_JSON))
          // then
          .andExpect(status().isForbidden());

      verify(positionService, never()).delete(positionId);
    }
  }
}
