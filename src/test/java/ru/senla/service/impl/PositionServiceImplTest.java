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
import ru.senla.data.PositionTestData;
import ru.senla.exception.EntityAlreadyExistsException;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.PositionMapper;
import ru.senla.repository.api.PositionRepository;

@ExtendWith(MockitoExtension.class)
public class PositionServiceImplTest {
  private static final String ERROR_MESSAGE = "Position with ID -1 was not found";
  private static final String ERROR_MESSAGE_ENTITY_EXIST =
      "Position with 'DEVELOPER' already exists";
  @Mock private PositionMapper positionMapper;
  @Mock private PositionRepository positionRepository;

  @InjectMocks private PositionServiceImpl positionService;

  @Nested
  class Create {
    @Test
    void createShouldReturnPositionResponse() {
      // given
      var positionRequest = PositionTestData.builder().build().buildPositionRequest();
      var position = PositionTestData.builder().build().buildPosition();
      var expectedResponse = PositionTestData.builder().build().buildPositionResponse();

      when(positionMapper.toPosition(positionRequest)).thenReturn(position);
      when(positionRepository.save(position)).thenReturn(position);
      when(positionMapper.toPositionResponse(position)).thenReturn(expectedResponse);

      // when
      var actualResponse = positionService.create(positionRequest);

      // then
      verify(positionRepository).save(position);
      verify(positionMapper).toPosition(positionRequest);
      verify(positionMapper).toPositionResponse(position);
      assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateShouldThrowEntityAlreadyExistsException() {
      // given
      var positionRequest = PositionTestData.builder().build().buildPositionRequest();
      // when
      when(positionRepository.existsByName(positionRequest.name())).thenReturn(Boolean.TRUE);

      var exception =
          assertThrows(
              EntityAlreadyExistsException.class, () -> positionService.create(positionRequest));

      // then
      assertEquals(ERROR_MESSAGE_ENTITY_EXIST, exception.getMessage());
    }
  }

  @Nested
  class GetAll {
    @Test
    void getAllShouldReturnListOfPositionResponses() {
      // given
      var pageable = Pageable.ofSize(2);
      var positions = List.of(PositionTestData.builder().build().buildPosition());
      var expectedResponses = List.of(PositionTestData.builder().build().buildPositionResponse());

      var departmentPage = new PageImpl<>(positions, pageable, 2);

      doReturn(departmentPage).when(positionRepository).findAll(pageable);

      IntStream.range(0, positions.size())
          .forEach(
              i ->
                  doReturn(expectedResponses.get(i))
                      .when(positionMapper)
                      .toPositionResponse(positions.get(i)));

      // when
      var actualResponses = positionService.getAll(pageable).getContent();

      // then
      assertEquals(expectedResponses, actualResponses);
    }
  }

  @Nested
  class GetById {
    @Test
    void getByIdShouldReturnExpectedPositionResponse() {
      // given
      var position = PositionTestData.builder().build().buildPosition();
      var expectedResponse = PositionTestData.builder().build().buildPositionResponse();

      when(positionRepository.findById(position.getId())).thenReturn(Optional.of(position));
      when(positionMapper.toPositionResponse(position)).thenReturn(expectedResponse);

      // when
      var actualResponse = positionService.getById(position.getId());

      // then
      assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getByIdShouldThrowNotFoundException() {
      // given
      var id = -1L;
      // when
      var exception =
          assertThrows(EntityNotFoundException.class, () -> positionService.getById(id));

      // then
      assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
  }

  @Nested
  class Update {
    @Test
    void updateShouldReturnPositionResponse() {
      // given
      var positionRequest = PositionTestData.builder().build().buildPositionRequest();
      var expectedResponse = PositionTestData.builder().build().buildPositionResponse();
      var position = PositionTestData.builder().build().buildPosition();
      var id = PositionTestData.builder().build().buildPosition().getId();
      when(positionRepository.findById(position.getId())).thenReturn(Optional.of(position));
      when(positionMapper.update(positionRequest, position)).thenReturn(position);
      when(positionRepository.save(position)).thenReturn(position);
      when(positionMapper.toPositionResponse(position)).thenReturn(expectedResponse);

      // when
      var actualResponse = positionService.update(id, positionRequest);

      // then
      assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateShouldThrowEntityAlreadyExistsException() {
      // given
      var positionRequest = PositionTestData.builder().build().buildPositionRequest();
      var id = PositionTestData.builder().build().buildPosition().getId();
      // when
      when(positionRepository.existsByName(positionRequest.name())).thenReturn(Boolean.TRUE);

      var exception =
          assertThrows(
              EntityAlreadyExistsException.class,
              () -> positionService.update(id, positionRequest));

      // then
      assertEquals(ERROR_MESSAGE_ENTITY_EXIST, exception.getMessage());
    }
  }

  @Nested
  class Delete {
    @Test
    void deleteShouldCallDaoDeleteMethodThrowEntityNotFoundException() {
      // given
      var id = PositionTestData.builder().build().buildPosition().getId();
      var position = PositionTestData.builder().build().buildPosition();
      // when
      when(positionRepository.findById(position.getId())).thenReturn(Optional.of(position));
      // then
      assertThatThrownBy(() -> positionService.delete(id))
          .isInstanceOf(EntityNotFoundException.class);
    }
  }
}
