package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
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
import ru.senla.service.api.NotificationService;
import ru.senla.service.api.TaskService;
import ru.senla.util.TaskSpecificationGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Implementation of the TaskService interface for managing tasks.
 * Provides CRUD operations for tasks, task history, and scheduled notifications for due dates.
 */
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
    private final NotificationService notificationService;

    /**
     * Creates a new task based on the provided request.
     *
     * @param taskRequest the request containing task details
     * @return the response containing the created task details
     */
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

    /**
     * Retrieves a paginated list of all tasks.
     *
     * @param pageable pagination information
     * @return a page of task responses
     */
    @Override
    public Page<TaskResponse> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toTaskResponse);
    }

    /**
     * Retrieves a paginated list of tasks based on the provided filter.
     *
     * @param taskFilter the filter criteria for tasks
     * @param pageable   pagination information
     * @return a page of task responses matching the filter
     */
    @Override
    public Page<TaskResponse> getAllByFilter(TaskFilter taskFilter, Pageable pageable) {
        return taskRepository.findAll(TaskSpecificationGenerator.filterToSpecification(taskFilter), pageable)
                .map(taskMapper::toTaskResponse);
    }

    /**
     * Retrieves a paginated list of task history entries for a specific task.
     *
     * @param taskId   the ID of the task for which to retrieve history
     * @param pageable  pagination information
     * @return a page of task history responses for the specified task
     */
    @Override
    public Page<TaskHistoryResponse> getAllTaskHistory(Long taskId, Pageable pageable) {
        return taskHistoryRepository.findTaskHistoryByTaskId(taskId, pageable)
                .map(taskHistoryMapper::toTaskHistoryResponse);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return the response containing the task details
     * @throws EntityNotFoundException if the task with the given ID is not found
     */
    @Override
    public TaskResponse getById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toTaskResponse)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, id));
    }

    /**
     * Updates an existing task with the provided details.
     *
     * @param id          the ID of the task to update
     * @param taskRequest the request containing updated task details
     * @return the response containing the updated task details
     * @throws EntityNotFoundException if the task with the given ID is not found
     */
    @Override
    @Transactional
    public TaskResponse update(Long id, TaskRequest taskRequest) {
        var currentTask = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Task.class, id));

        var userProfile = userProfileRepository.findById(taskRequest.assignedTo())
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));

        var project = projectRepository.findById(taskRequest.project())
                .orElseThrow(() -> new EntityNotFoundException(Project.class, id));

        currentTask.setName(taskRequest.name());
        currentTask.setAssignedTo(userProfile);
        currentTask.setProject(project);
        currentTask.setDueDate(taskRequest.dueDate());
        currentTask.setStatus(taskRequest.status());
        currentTask.setPriority(taskRequest.priority());

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

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     */
    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Sends reminders for tasks due within the next 24 hours.
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDueDateReminders() {
        taskRepository.findAllByDueDateBetween(LocalDateTime.now(), LocalDateTime.now().plus(24, ChronoUnit.HOURS))
                .forEach(notificationService::sendNotification);
    }
}
