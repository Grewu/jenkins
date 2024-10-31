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

/**
 * Implementation of the TaskHistoryService interface that provides methods for managing task
 * histories.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskHistoryServiceImpl implements TaskHistoryService {

  private final TaskHistoryMapper taskHistoryMapper;
  private final TaskHistoryRepository taskHistoryRepository;

  /**
   * Creates a new task history entry based on the provided request.
   *
   * @param taskHistoryRequest the request containing task history details
   * @return the response containing the created task history entry
   */
  @Override
  @Transactional
  public TaskHistoryResponse create(TaskHistoryRequest taskHistoryRequest) {
    var taskHistory = taskHistoryMapper.toTaskHistory(taskHistoryRequest);
    return taskHistoryMapper.toTaskHistoryResponse(taskHistoryRepository.save(taskHistory));
  }

  /**
   * Retrieves a paginated list of all task history entries.
   *
   * @param pageable pagination information
   * @return a page of task history responses
   */
  @Override
  public Page<TaskHistoryResponse> getAll(Pageable pageable) {
    return taskHistoryRepository.findAll(pageable).map(taskHistoryMapper::toTaskHistoryResponse);
  }

  /**
   * Retrieves a task history entry by its ID.
   *
   * @param id the ID of the task history entry
   * @return the response containing the task history entry details
   * @throws EntityNotFoundException if the task history entry with the given ID is not found
   */
  @Override
  public TaskHistoryResponse getById(Long id) {
    return taskHistoryRepository
        .findById(id)
        .map(taskHistoryMapper::toTaskHistoryResponse)
        .orElseThrow(() -> new EntityNotFoundException(TaskHistory.class, id));
  }

  /**
   * Updates an existing task history entry with the provided details.
   *
   * @param id the ID of the task history entry to update
   * @param taskHistoryRequest the request containing updated task history details
   * @return the response containing the updated task history entry
   * @throws EntityNotFoundException if the task history entry with the given ID is not found
   */
  @Override
  @Transactional
  public TaskHistoryResponse update(Long id, TaskHistoryRequest taskHistoryRequest) {
    return taskHistoryRepository
        .findById(id)
        .map(currentHistory -> taskHistoryMapper.update(taskHistoryRequest, currentHistory))
        .map(taskHistoryRepository::save)
        .map(taskHistoryMapper::toTaskHistoryResponse)
        .orElseThrow(() -> new EntityNotFoundException(TaskHistory.class, id));
  }

  /**
   * Deletes a task history entry by its ID.
   *
   * @param id the ID of the task history entry to delete
   */
  @Override
  @Transactional
  public void delete(Long id) {
    taskHistoryRepository.deleteById(id);
  }
}
