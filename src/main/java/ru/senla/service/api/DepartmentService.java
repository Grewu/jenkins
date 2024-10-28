package ru.senla.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.senla.model.dto.request.DepartmentRequest;
import ru.senla.model.dto.response.DepartmentResponse;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.service.AbstractService;

/**
 * Service interface for managing departments within the application.
 * <p>
 * This interface defines the operations for creating, retrieving, updating, and deleting
 * departments, as well as retrieving user profiles associated with a specific department.
 * </p>
 */
public interface DepartmentService extends AbstractService<Long, DepartmentRequest, DepartmentResponse> {

    /**
     * Retrieves all user profiles associated with the specified department.
     *
     * @param departmentId the ID of the department for which to retrieve user profiles
     * @param pageable     pagination information
     * @return a paginated list of user profiles associated with the specified department
     */
    Page<UserProfileResponse> getAllUsersProfileByDepartmentId(Long departmentId, Pageable pageable);
}
