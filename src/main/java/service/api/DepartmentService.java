package service.api;

import model.dto.request.DepartmentRequest;
import model.dto.response.DepartmentResponse;
import service.AbstractService;

public interface DepartmentService extends AbstractService<Long, DepartmentRequest, DepartmentResponse> {
}
