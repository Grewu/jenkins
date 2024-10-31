package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.PositionMapper;
import ru.senla.model.dto.request.PositionRequest;
import ru.senla.model.dto.response.PositionResponse;
import ru.senla.model.entity.Position;
import ru.senla.repository.api.PositionRepository;
import ru.senla.service.api.PositionService;

/**
 * The PositionServiceImpl class implements the PositionService interface to provide CRUD operations
 * for Position entities.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PositionServiceImpl implements PositionService {

  private final PositionRepository positionRepository;
  private final PositionMapper positionMapper;

  /**
   * Creates a new position based on the provided request data.
   *
   * @param positionRequest the data for the new position
   * @return the created PositionResponse
   */
  @Override
  @Transactional
  public PositionResponse create(PositionRequest positionRequest) {
    var position = positionMapper.toPosition(positionRequest);
    return positionMapper.toPositionResponse(positionRepository.save(position));
  }

  /**
   * Retrieves all positions with pagination support.
   *
   * @param pageable pagination information
   * @return a page of PositionResponse objects
   */
  @Override
  public Page<PositionResponse> getAll(Pageable pageable) {
    return positionRepository.findAll(pageable).map(positionMapper::toPositionResponse);
  }

  /**
   * Retrieves a position by its ID.
   *
   * @param id the ID of the position
   * @return the PositionResponse for the specified ID
   * @throws EntityNotFoundException if the position is not found
   */
  @Override
  public PositionResponse getById(Long id) {
    return positionRepository
        .findById(id)
        .map(positionMapper::toPositionResponse)
        .orElseThrow(() -> new EntityNotFoundException(Position.class, id));
  }

  /**
   * Updates an existing position based on the provided ID and request data.
   *
   * @param id the ID of the position to update
   * @param positionRequest the new data for the position
   * @return the updated PositionResponse
   * @throws EntityNotFoundException if the position is not found
   */
  @Override
  @Transactional
  public PositionResponse update(Long id, PositionRequest positionRequest) {
    return positionRepository
        .findById(id)
        .map(current -> positionMapper.update(positionRequest, current))
        .map(positionRepository::save)
        .map(positionMapper::toPositionResponse)
        .orElseThrow(() -> new EntityNotFoundException(Position.class, id));
  }

  /**
   * Deletes a position by its ID.
   *
   * @param id the ID of the position to delete
   */
  @Override
  @Transactional
  public void delete(Long id) {
    positionRepository.deleteById(id);
  }
}
