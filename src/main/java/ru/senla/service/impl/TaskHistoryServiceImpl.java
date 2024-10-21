package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.TaskHistoryMapper;
import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.entity.Task;
import ru.senla.model.entity.TaskHistory;
import ru.senla.model.entity.User;
import ru.senla.repository.api.TaskHistoryRepository;
import ru.senla.service.api.TaskHistoryService;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private final TaskHistoryRepository taskHistoryRepository;
    private final TaskHistoryMapper taskHistoryMapper;

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

    @Override
    @Transactional
    public void recordTaskUpdateHistory(Task taskToSave, Task currentTask) {
        var taskHistoryRequest = new TaskHistoryRequest(
                currentTask.getId(),
                currentTask.getName(),
                currentTask.getProject().getId(),
                currentTask.getAssignedTo().getId(),
                currentTask.getCreatedBy().getId(),
                currentTask.getDueDate(),
                currentTask.getStatus(),
                currentTask.getPriority(),
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
                LocalDateTime.now(),
                "Task was updated"
        );
        var taskHistory = taskHistoryMapper.toTaskHistory(taskHistoryRequest);
        taskHistoryRepository.save(taskHistory);
    }


    @Override
    @Transactional
    public void recordTaskCreationHistory(Task task) {
        var taskHistoryRequest = new TaskHistoryRequest(
                task.getId(),
                task.getName(),
                task.getProject().getId(),
                task.getAssignedTo().getId(),
                task.getCreatedBy().getId(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority(),
                task.getCreatedBy().getId(),
                LocalDateTime.now(),
                "Task was created"
        );

        var taskHistory = taskHistoryMapper.toTaskHistory(taskHistoryRequest);
        taskHistoryRepository.save(taskHistory);
    }

}
