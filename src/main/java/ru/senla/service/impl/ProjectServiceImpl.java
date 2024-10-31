package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.ProjectMapper;
import ru.senla.mapper.TaskMapper;
import ru.senla.model.dto.request.ProjectRequest;
import ru.senla.model.dto.response.ProjectResponse;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.model.entity.Project;
import ru.senla.model.entity.UserProfile;
import ru.senla.repository.api.ProjectRepository;
import ru.senla.repository.api.TaskRepository;
import ru.senla.repository.api.UserProfileRepository;
import ru.senla.service.api.ProjectService;

/** Implementation of the ProjectService interface that provides methods for managing projects. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

  private final ProjectMapper projectMapper;
  private final TaskMapper taskMapper;
  private final ProjectRepository projectRepository;
  private final UserProfileRepository userProfileRepository;
  private final TaskRepository taskRepository;

  /**
   * Creates a new project based on the provided project request.
   *
   * @param projectRequest the request containing project details
   * @return the response containing the created project
   */
  @Override
  @Transactional
  public ProjectResponse create(ProjectRequest projectRequest) {
    var project = projectMapper.toProject(projectRequest);
    return projectMapper.toProjectResponse(projectRepository.save(project));
  }

  /**
   * Retrieves a paginated list of all projects.
   *
   * @param pageable pagination information
   * @return a page of project responses
   */
  @Override
  public Page<ProjectResponse> getAll(Pageable pageable) {
    return projectRepository.findAll(pageable).map(projectMapper::toProjectResponse);
  }

  /**
   * Retrieves a paginated list of tasks related to a specific project.
   *
   * @param projectId the ID of the project
   * @param pageable pagination information
   * @return a page of task responses related to the project
   */
  @Override
  public Page<TaskResponse> getAllTaskRelatedToProjectByProjectId(
      Long projectId, Pageable pageable) {
    return taskRepository.findTasksByProjectId(projectId, pageable).map(taskMapper::toTaskResponse);
  }

  /**
   * Retrieves a project by its ID.
   *
   * @param id the ID of the project
   * @return the response containing the project details
   * @throws EntityNotFoundException if the project with the given ID is not found
   */
  @Override
  public ProjectResponse getById(Long id) {
    return projectRepository
        .findById(id)
        .map(projectMapper::toProjectResponse)
        .orElseThrow(() -> new EntityNotFoundException(Project.class, id));
  }

  /**
   * Updates an existing project with the provided details.
   *
   * @param id the ID of the project to update
   * @param projectRequest the request containing updated project details
   * @return the response containing the updated project
   * @throws EntityNotFoundException if the project with the given ID is not found
   */
  @Override
  @Transactional
  public ProjectResponse update(Long id, ProjectRequest projectRequest) {
    var currentProject =
        projectRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(Project.class, id));

    var userProfile =
        userProfileRepository
            .findById(projectRequest.owner())
            .orElseThrow(
                () -> new EntityNotFoundException(UserProfile.class, projectRequest.owner()));

    currentProject.setName(projectRequest.name());
    currentProject.setProjectCode(projectRequest.projectCode());
    currentProject.setDescription(projectRequest.description());
    currentProject.setStartDate(projectRequest.startDate());
    currentProject.setEndDate(projectRequest.endDate());
    currentProject.setOwner(userProfile);

    return projectMapper.toProjectResponse(projectRepository.save(currentProject));
  }

  /**
   * Deletes a project by its ID.
   *
   * @param id the ID of the project to delete
   */
  @Override
  @Transactional
  public void delete(Long id) {
    projectRepository.deleteById(id);
  }
}
