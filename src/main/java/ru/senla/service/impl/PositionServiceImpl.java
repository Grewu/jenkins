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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    @Override
    @Transactional
    public PositionResponse create(PositionRequest positionRequest) {
        var department = positionMapper.toPosition(positionRequest);
        return positionMapper.toPositionResponse(positionRepository.save(department));
    }

    @Override
    public Page<PositionResponse> getAll(Pageable pageable) {
        return positionRepository.findAll(pageable)
                .map(positionMapper::toPositionResponse);
    }

    @Override
    public PositionResponse getById(Long id) {
        return positionRepository.findById(id)
                .map(positionMapper::toPositionResponse)
                .orElseThrow(() -> new EntityNotFoundException(Position.class, id));
    }

    @Override
    @Transactional
    public PositionResponse update(Long id, PositionRequest departmentRequest) {
        return positionRepository.findById(id)
                .map(current -> positionMapper.update(departmentRequest, current))
                .map(positionRepository::save)
                .map(positionMapper::toPositionResponse)
                .orElseThrow(() -> new EntityNotFoundException(Position.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        positionRepository.deleteById(id);
    }

}
