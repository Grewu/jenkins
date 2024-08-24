package service.api;

import model.dto.request.EmployeeRequest;
import model.dto.response.EmployeeResponse;
import service.AbstractService;

public interface EmployeeService extends AbstractService<Long, EmployeeRequest, EmployeeResponse> {
}
