package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.TaskMapper;
import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.request.TaskRequest;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.model.entity.Task;
import ru.senla.repository.api.TaskRepository;
import ru.senla.service.api.TaskHistoryService;
import ru.senla.service.api.TaskService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
//TODO: Duplicate UPDATE
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final TaskHistoryService taskHistoryService;

    @Override
    @Transactional
    public TaskResponse create(TaskRequest taskRequest) {
        var task = taskMapper.toTask(taskRequest);
        var taskToSave = taskRepository.save(task);
        var taskHistoryRequest = new TaskHistoryRequest(
                task.getId(),
                task.getName(),
                task.getProject().getId(),
                task.getAssignedTo().getId(),
                task.getCreatedBy().getId(), task.getDueDate(),
                task.getStatus(), task.getPriority(),
                task.getCreatedBy().getId(),
                LocalDateTime.now(),
                "Task was created");
        taskHistoryService.create(taskHistoryRequest);
        return taskMapper.toTaskResponse(taskToSave);
    }

    @Override
    public Page<TaskResponse> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toTaskResponse);
    }

    @Override
    public TaskResponse getById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toTaskResponse)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, id));
    }

    @Override
    @Transactional
    //TODO:ADD DESCRIPTION TASK HISTORY ADD ID HOW DID
    public TaskResponse update(Long id, TaskRequest taskRequest) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, id));

        var updatedTask = taskMapper.update(taskRequest, task);
        var taskToSave = taskRepository.save(updatedTask);

        var taskHistoryRequest = new TaskHistoryRequest(
                taskToSave.getId(),
                taskToSave.getName(),
                taskToSave.getProject().getId(),
                taskToSave.getAssignedTo().getId(),
                taskToSave.getCreatedBy().getId(),
                taskToSave.getDueDate(),
                taskToSave.getStatus(),
                taskToSave.getPriority(),
                taskToSave.getCreatedBy().getId(),
                LocalDateTime.now(),
                "Task was updated");

        taskHistoryService.create(taskHistoryRequest);

        return taskMapper.toTaskResponse(taskToSave);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

}
