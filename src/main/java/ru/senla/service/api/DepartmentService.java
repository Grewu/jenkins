package ru.senla.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.senla.model.dto.request.DepartmentRequest;
import ru.senla.model.dto.response.DepartmentResponse;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.service.AbstractService;

public interface DepartmentService extends AbstractService<Long, DepartmentRequest, DepartmentResponse> {
    Page<UserProfileResponse> getAllUsersProfileByDepartmentId(Long departmentId, Pageable pageable);
}
