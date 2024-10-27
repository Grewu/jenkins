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
import ru.senla.mapper.TaskMapper;
import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.request.TaskRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.model.entity.Project;
import ru.senla.model.entity.Task;
import ru.senla.model.entity.User;
import ru.senla.model.entity.UserProfile;
import ru.senla.model.filter.TaskFilter;
import ru.senla.repository.api.ProjectRepository;
import ru.senla.repository.api.TaskHistoryRepository;
import ru.senla.repository.api.TaskRepository;
import ru.senla.repository.api.UserProfileRepository;
import ru.senla.service.api.TaskService;
import ru.senla.util.TaskSpecificationGenerator;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskHistoryMapper taskHistoryMapper;
    private final TaskRepository taskRepository;
    private final UserProfileRepository userProfileRepository;
    private final ProjectRepository projectRepository;
    private final TaskHistoryRepository taskHistoryRepository;

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
        return taskMapper.toTaskResponse(taskToSave);
    }

    @Override
    public Page<TaskResponse> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toTaskResponse);
    }

    @Override
    public Page<TaskResponse> getAllByFilter(TaskFilter taskFilter, Pageable pageable) {
        return taskRepository.findAll(TaskSpecificationGenerator.filterToSpecification(taskFilter), pageable)
                .map(taskMapper::toTaskResponse);
    }

    @Override
    public Page<TaskHistoryResponse> getAllTaskHistory(Long taskId, Pageable pageable) {
        return taskHistoryRepository.findTaskHistoryByTaskId(taskId, pageable)
                .map(taskHistoryMapper::toTaskHistoryResponse);
    }

    @Override
    public TaskResponse getById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toTaskResponse)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, id));
    }

    @Override
    @Transactional
    public TaskResponse update(Long id, TaskRequest taskRequest) {
        var currentTask = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, id));

        var userProfile = userProfileRepository.findById(taskRequest.assignedTo())
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));

        var project = projectRepository.findById(taskRequest.project())
                .orElseThrow(() -> new EntityNotFoundException(Project.class, id));

        currentTask.setAssignedTo(userProfile);
        currentTask.setProject(project);

        var updatedTask = taskMapper.update(taskRequest, currentTask);
        var taskToSave = taskRepository.save(updatedTask);

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

        return taskMapper.toTaskResponse(taskToSave);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
