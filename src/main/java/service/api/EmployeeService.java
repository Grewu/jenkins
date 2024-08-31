package service.api;

import model.dto.request.EmployeeRequest;
import model.dto.response.CommentResponse;
import model.dto.response.EmployeeResponse;
import service.AbstractService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EmployeeService extends AbstractService<Long, EmployeeRequest, EmployeeResponse> {

    Map<EmployeeResponse, List<CommentResponse>> getAllEmployeesWithComments();

    Map<EmployeeResponse, List<CommentResponse>> getEmployeeCommentsByDateRange(LocalDateTime startDate,
                                                                                LocalDateTime endDate);

    Map<EmployeeResponse, List<CommentResponse>> getEmployeeCommentsByDepartmentId(Long departmentId);

}
