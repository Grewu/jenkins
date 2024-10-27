package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.TaskHistoryMapper;
import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.entity.TaskHistory;
import ru.senla.repository.api.TaskHistoryRepository;
import ru.senla.service.api.TaskHistoryService;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private final TaskHistoryMapper taskHistoryMapper;
    private final TaskHistoryRepository taskHistoryRepository;

    @Override
    @Transactional
    public TaskHistoryResponse create(TaskHistoryRequest taskHistoryRequest) {
        var taskHistory = taskHistoryMapper.toTaskHistory(taskHistoryRequest);
        return taskHistoryMapper.toTaskHistoryResponse(taskHistoryRepository.save(taskHistory));
    }

    @Override
    public Page<TaskHistoryResponse> getAll(Pageable pageable) {
        return taskHistoryRepository.findAll(pageable)
                .map(taskHistoryMapper::toTaskHistoryResponse);
    }

    @Override
    public TaskHistoryResponse getById(Long id) {
        return taskHistoryRepository.findById(id)
                .map(taskHistoryMapper::toTaskHistoryResponse)
                .orElseThrow(() -> new EntityNotFoundException(TaskHistory.class, id));
    }

    @Override
    @Transactional
    public TaskHistoryResponse update(Long id, TaskHistoryRequest taskHistoryRequest) {
        return taskHistoryRepository.findById(id)
                .map(currentHistory -> taskHistoryMapper.update(taskHistoryRequest, currentHistory))
                .map(taskHistoryRepository::save)
                .map(taskHistoryMapper::toTaskHistoryResponse)
                .orElseThrow(() -> new EntityNotFoundException(TaskHistory.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskHistoryRepository.deleteById(id);
    }

}
