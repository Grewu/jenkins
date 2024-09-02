package mapper;

import model.dto.request.EmployeeRequest;
import model.dto.response.CommentResponse;
import model.dto.response.EmployeeResponse;
import model.entity.Comment;
import model.entity.Employee;
import model.entity.enums.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    private final CommentMapper commentMapper;

    @Autowired
    public EmployeeMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public Employee toEmployee(EmployeeRequest employeeRequest) {
        return new Employee.Builder()
                .setId(employeeRequest.id())
                .setFirstName(employeeRequest.firstName())
                .setLastName(employeeRequest.lastName())
                .setPosition(employeeRequest.position())
                .setDepartmentId(employeeRequest.departmentId())
                .setEmail(employeeRequest.email())
                .setPassword(employeeRequest.password())
                .build();
    }

    public EmployeeResponse toEmployeeResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPosition(),
                employee.getDepartmentId(),
                employee.getEmail()
        );
    }

    public static Employee mapRow(ResultSet resultSet) throws SQLException {
        return new Employee.Builder()
                .setId(resultSet.getLong("id"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setPosition(Position.valueOf(resultSet.getString("position").trim().toUpperCase()
                        .replace(" ", "_")))
                .setDepartmentId(resultSet.getLong("department_id"))
                .setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"))
                .build();
    }

    public static Employee mapRowWithoutPassword(ResultSet resultSet) throws SQLException {
        return new Employee.Builder()
                .setId(resultSet.getLong("id"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setPosition(Position.valueOf(resultSet.getString("position").trim().toUpperCase()
                        .replace(" ", "_")))
                .setDepartmentId(resultSet.getLong("department_id"))
                .setEmail(resultSet.getString("email"))
                .build();
    }

    public static Employee mapRowWithoutDepartmentAndEmailPassword(ResultSet resultSet) throws SQLException {
        return new Employee.Builder()
                .setId(resultSet.getLong("id"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setPosition(Position.valueOf(resultSet.getString("position").trim().toUpperCase()
                        .replace(" ", "_")))
                .build();
    }

    public Map<EmployeeResponse, List<CommentResponse>> toMapOfEmployeeResponse(Map<Employee, List<Comment>> allEmployeesWithComments) {
        return allEmployeesWithComments.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> toEmployeeResponse(entry.getKey()),
                        entry -> entry.getValue().stream()
                                .map(commentMapper::toCommentResponse)
                                .collect(Collectors.toList())
                ));
    }
}
