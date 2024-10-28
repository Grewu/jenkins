package ru.senla.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.senla.model.dto.request.ProjectRequest;
import ru.senla.model.dto.response.ProjectResponse;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.service.AbstractService;

/**
 * Service interface for managing projects within the application.
 * <p>
 * This interface defines the operations for creating, updating, deleting, and retrieving projects.
 * It extends the {@link AbstractService} for generic CRUD operations and adds project-specific functionality.
 * </p>
 */
public interface ProjectService extends AbstractService<Long, ProjectRequest, ProjectResponse> {

    /**
     * Retrieves all tasks related to a specific project by its ID.
     *
     * @param projectId the ID of the project for which related tasks are to be retrieved
     * @param pageable  pagination information including page number and size
     * @return a paginated list of tasks that are related to the specified project
     */
    Page<TaskResponse> getAllTaskRelatedToProjectByProjectId(Long projectId, Pageable pageable);
}
