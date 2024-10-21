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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserProfileRepository userProfileRepository;
    private final DepartmentMapper departmentMapper;
    private final UserProfileMapper userProfileMapper;

    @Override
    @Transactional
    public DepartmentResponse create(DepartmentRequest departmentRequest) {
        var department = departmentMapper.toDepartment(departmentRequest);
        return departmentMapper.toDepartmentResponse(departmentRepository.save(department));
    }

    @Override
    public Page<DepartmentResponse> getAll(Pageable pageable) {
        return departmentRepository.findAll(pageable)
                .map(departmentMapper::toDepartmentResponse);
    }

    @Override
    public DepartmentResponse getById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDepartmentResponse)
                .orElseThrow(() -> new EntityNotFoundException(Department.class, id));
    }

    @Override
    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest departmentRequest) {
        return departmentRepository.findById(id)
                .map(current -> departmentMapper.update(departmentRequest, current))
                .map(departmentRepository::save)
                .map(departmentMapper::toDepartmentResponse)
                .orElseThrow(() -> new EntityNotFoundException(Department.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Page<UserProfileResponse> getAllUsersProfileByDepartmentId(Long departmentId, Pageable pageable) {
        return userProfileRepository.findByDepartmentId(departmentId, pageable)
                .map(userProfileMapper::toUserProfileResponse);
    }
}
