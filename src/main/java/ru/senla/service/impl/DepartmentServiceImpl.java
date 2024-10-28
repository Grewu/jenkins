package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.DepartmentMapper;
import ru.senla.mapper.UserProfileMapper;
import ru.senla.model.dto.request.DepartmentRequest;
import ru.senla.model.dto.response.DepartmentResponse;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.model.entity.Department;
import ru.senla.repository.api.DepartmentRepository;
import ru.senla.repository.api.UserProfileRepository;
import ru.senla.service.api.DepartmentService;

/**
 * The DepartmentServiceImpl class implements the DepartmentService interface
 * to provide business logic for managing departments. It handles the creation,
 * retrieval, updating, and deletion of departments while ensuring transactional integrity.
 * The service uses a DepartmentMapper to convert between entity and DTO objects
 * and a UserProfileMapper to manage user profiles associated with departments.
 *
 * @see DepartmentService
 * @see DepartmentMapper
 * @see UserProfileMapper
 * @see DepartmentRequest
 * @see DepartmentResponse
 * @see UserProfileResponse
 * @see Department
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserProfileRepository userProfileRepository;
    private final DepartmentMapper departmentMapper;
    private final UserProfileMapper userProfileMapper;

    /**
     * Creates a new department based on the provided request data.
     *
     * @param departmentRequest the request object containing the details of the department to be created.
     * @return a DepartmentResponse object representing the created department.
     */
    @Override
    @Transactional
    public DepartmentResponse create(DepartmentRequest departmentRequest) {
        var department = departmentMapper.toDepartment(departmentRequest);
        return departmentMapper.toDepartmentResponse(departmentRepository.save(department));
    }

    /**
     * Retrieves all departments in a paginated format.
     *
     * @param pageable pagination information to control the number of departments returned.
     * @return a Page of DepartmentResponse objects containing the departments.
     */
    @Override
    public Page<DepartmentResponse> getAll(Pageable pageable) {
        return departmentRepository.findAll(pageable)
                .map(departmentMapper::toDepartmentResponse);
    }

    /**
     * Retrieves a specific department by its ID.
     *
     * @param id the ID of the department to retrieve.
     * @return a DepartmentResponse object representing the retrieved department.
     * @throws EntityNotFoundException if the department with the specified ID does not exist.
     */
    @Override
    public DepartmentResponse getById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDepartmentResponse)
                .orElseThrow(() -> new EntityNotFoundException(Department.class, id));
    }

    /**
     * Updates an existing department identified by its ID with the provided request data.
     *
     * @param id the ID of the department to update.
     * @param departmentRequest the request object containing the updated department details.
     * @return a DepartmentResponse object representing the updated department.
     * @throws EntityNotFoundException if the department with the specified ID does not exist.
     */
    @Override
    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest departmentRequest) {
        return departmentRepository.findById(id)
                .map(current -> departmentMapper.update(departmentRequest, current))
                .map(departmentRepository::save)
                .map(departmentMapper::toDepartmentResponse)
                .orElseThrow(() -> new EntityNotFoundException(Department.class, id));
    }

    /**
     * Deletes the department identified by its ID.
     *
     * @param id the ID of the department to delete.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    /**
     * Retrieves all user profiles associated with a specific department in a paginated format.
     *
     * @param departmentId the ID of the department to retrieve user profiles for.
     * @param pageable pagination information to control the number of user profiles returned.
     * @return a Page of UserProfileResponse objects containing the user profiles associated with the department.
     */
    @Override
    public Page<UserProfileResponse> getAllUsersProfileByDepartmentId(Long departmentId, Pageable pageable) {
        return userProfileRepository.findByDepartmentId(departmentId, pageable)
                .map(userProfileMapper::toUserProfileResponse);
    }
}
