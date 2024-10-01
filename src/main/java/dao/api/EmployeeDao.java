package dao.api;

import dao.AbstractDao;
import model.entity.Comment;
import model.entity.Employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EmployeeDao extends AbstractDao<Long, Employee> {
    Map<Employee, List<Comment>> getAllEmployeesWithComments();

    Map<Employee, List<Comment>> getEmployeeCommentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    Map<Employee, List<Comment>> getEmployeeCommentsByDepartmentId(Long departmentId);
}
