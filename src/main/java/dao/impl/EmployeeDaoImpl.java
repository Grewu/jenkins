package dao.impl;

import dao.api.EmployeeDao;
import mapper.CommentMapper;
import mapper.EmployeeMapper;
import model.entity.Comment;
import model.entity.Employee;
import org.springframework.stereotype.Repository;
import util.ConnectionHolder;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.entity.Employee;
import model.entity.enums.Position;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private static final String INSERT_INTO_EMPLOYEE = """
            INSERT INTO employees (id, first_name, last_name, position, department_id, email, password)
            VALUES (?, ?, ?, ?, ?, ?, ?)""";

    private static final String FIND_ALL_EMPLOYEES = """
            SELECT * FROM employees""";

    private static final String FIND_BY_ID = """
            SELECT id, first_name, last_name, position, department_id, email, password
            FROM employees WHERE id = ?""";

    private static final String UPDATE_EMPLOYEE = """
            UPDATE employees
            SET first_name = ?,
                last_name = ?,
                position = ?,
                department_id = ?,
                email = ?,
                password = ?
            WHERE id = ?""";

    private static final String DELETE_EMPLOYEE_BY_ID = """
            DELETE FROM employees WHERE id = ?""";

    private static final String GET_ALL_EMPLOYEE_WITH_COMMENTS = """
            SELECT e.id,
                   e.first_name,
                   e.last_name,
                   e.position,
                   c.id,
                   c.comment_text,
                   c.created_at,
                   c.task_id
            FROM employees e
                     LEFT JOIN comments c ON e.id = c.id;
                        
            """;

    private static final String SELECT_EMPLOYEE_COMMENTS_BY_DATE_RANGE = """
                SELECT e.id,
                   e.first_name,
                   e.last_name,
                   e.position,
                   c.id,
                   c.comment_text,
                   c.created_at
            FROM employees e
                     LEFT JOIN comments c ON e.id = c.id
            WHERE created_at BETWEEN ? AND ?;
            """;

    private static final String SELECT_EMPLOYEE_WITH_COMMENT_BY_DEPARTMENT_ID = """
            SELECT DISTINCT e.id,
                            e.first_name,
                            e.last_name,
                            e.position,
                            e.department_id,
                            e.email,
                            c.id,
                            c.task_id,
                            c.comment_text,
                            c.created_at
            FROM employees e
                     JOIN comments c ON e.id = c.employee_id
            WHERE e.department_id = ?;
            """;

    @Override
    public Employee create(Employee employee) {
        try {
            try (var connection = ConnectionHolder.getConnection();
                 var statement = connection.prepareStatement(INSERT_INTO_EMPLOYEE)) {
                statement.setLong(1, employee.getId());
                statement.setString(2, employee.getFirstName());
                statement.setString(3, employee.getLastName());
                statement.setString(4, employee.getPosition().toString());
                statement.setLong(5, employee.getDepartmentId());
                statement.setString(6, employee.getEmail());
                statement.setString(7, employee.getPassword());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee;

    private static final Map<Long, Employee> database = new HashMap<>();

    static {
        database.put(1L, new Employee.Builder()
                .setId(1L)
                .setFirstName("Alice")
                .setLastName("Smith")
                .setDepartmentId(1L)
                .setEmail("alice.smith@example.com")
                .setPassword("password_hash")
                .setPosition(Position.MANAGER)
                .build());

        database.put(2L, new Employee.Builder()
                .setId(2L)
                .setFirstName("Bob")
                .setLastName("Johnson")
                .setDepartmentId(2L)
                .setEmail("bob.johnson@example.com")
                .setPassword("password_hash")
                .setPosition(Position.DEVELOPER)
                .build());

        database.put(3L, new Employee.Builder()
                .setId(3L)
                .setFirstName("Charlie")
                .setLastName("Williams")
                .setDepartmentId(3L)
                .setEmail("charlie.williams@example.com")
                .setPassword("password_hash")
                .setPosition(Position.SALESPERSON)
                .build());

        database.put(4L, new Employee.Builder()
                .setId(4L)
                .setFirstName("David")
                .setLastName("Brown")
                .setDepartmentId(4L)
                .setEmail("david.brown@example.com")
                .setPassword("password_hash")
                .setPosition(Position.MARKETING_SPECIALIST)
                .build());
    }

    @Override
    public Employee create(Employee employee) {
        if (database.containsKey(employee.getId())) {
            throw new IllegalArgumentException("Key is already taken");
        }
        var newEmployee = new Employee.Builder()
                .setId(employee.getId())
                .setFirstName(employee.getFirstName())
                .setLastName(employee.getLastName())
                .setDepartmentId(employee.getDepartmentId())
                .setEmail(employee.getEmail())
                .setPassword(employee.getPassword())
                .setPosition(employee.getPosition())
                .build();
        database.put(employee.getId(), newEmployee);
        return newEmployee;

    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (var connection = ConnectionHolder.getConnection();
             var statement = connection.prepareStatement(FIND_ALL_EMPLOYEES)) {
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var employee = EmployeeMapper.mapRow(resultSet);
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }


    @Override
    public Optional<Employee> findById(Long id) {
        try {
            try (var connection = ConnectionHolder.getConnection();
                 var statement = connection.prepareStatement(FIND_BY_ID)) {
                statement.setLong(1, id);
                try (var resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        var employee = EmployeeMapper.mapRow(resultSet);
                        return Optional.of(employee);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();

        return List.copyOf(database.values());
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(database.get(id));

    }

    @Override
    public Employee update(Employee employee) {

        var existingEmployee = findById(employee.getId()).isPresent();
        if (existingEmployee) {
            try (var connection = ConnectionHolder.getConnection();
                 var statement = connection.prepareStatement(UPDATE_EMPLOYEE)) {
                statement.setString(1, employee.getFirstName());
                statement.setString(2, employee.getLastName());
                statement.setString(3, employee.getPosition().toString());
                statement.setLong(4, employee.getDepartmentId());
                statement.setString(5, employee.getEmail());
                statement.setString(6, employee.getPassword());
                statement.setLong(7, employee.getId());
                statement.executeUpdate();
                return employee;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Not found employee " + employee.getId());
        }


        var existingEmployee = database.get(employee.getId());
        if (existingEmployee != null) {
            var updateEmployee = new Employee.Builder()
                    .setId(existingEmployee.getId())
                    .setFirstName(employee.getFirstName())
                    .setLastName(employee.getLastName())
                    .setDepartmentId(employee.getDepartmentId())
                    .setEmail(employee.getEmail())
                    .setPassword(employee.getPassword())
                    .setPosition(employee.getPosition())
                    .build();
            database.put(existingEmployee.getId(), existingEmployee);
            return updateEmployee;
        }
        throw new IllegalArgumentException("Not found employee " + employee.getId());

    }

    @Override
    public boolean delete(Long id) {

        try (var connection = ConnectionHolder.getConnection();
             var statement = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Employee, List<Comment>> getAllEmployeesWithComments() {
        Map<Employee, List<Comment>> employeeCommentsMap = new HashMap<>();
        try (var connection = ConnectionHolder.getConnection();
             var statement = connection.prepareStatement(GET_ALL_EMPLOYEE_WITH_COMMENTS)) {
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var employee = EmployeeMapper.mapRowWithoutDepartmentAndEmailPassword(resultSet);
                    var comment = CommentMapper.mapRowWithoutEmployeeId(resultSet);
                    employeeCommentsMap.computeIfAbsent(employee, k -> new ArrayList<>()).add(comment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeCommentsMap;
    }

    @Override
    public Map<Employee, List<Comment>> getEmployeeCommentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Map<Employee, List<Comment>> employeeCommentsMap = new HashMap<>();
        try (var connection = ConnectionHolder.getConnection();
             var statement = connection.prepareStatement(SELECT_EMPLOYEE_COMMENTS_BY_DATE_RANGE)) {
            statement.setTimestamp(1, Timestamp.valueOf(startDate));
            statement.setTimestamp(2, Timestamp.valueOf(endDate));
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var employee = EmployeeMapper.mapRowWithoutDepartmentAndEmailPassword(resultSet);
                    var comment = CommentMapper.mapRowWithoutEmployeeIdAndCreatedAtAndTaskId(resultSet);
                    employeeCommentsMap.computeIfAbsent(employee, comments -> new ArrayList<>()).add(comment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeCommentsMap;
    }

    @Override
    public Map<Employee, List<Comment>> getEmployeeCommentsByDepartmentId(Long departmentId) {
        Map<Employee, List<Comment>> employeeCommentsMap = new HashMap<>();
        try (var connection = ConnectionHolder.getConnection();
             var statement = connection.prepareStatement(SELECT_EMPLOYEE_WITH_COMMENT_BY_DEPARTMENT_ID)) {
            statement.setLong(1, departmentId);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var employee = EmployeeMapper.mapRowWithoutPassword(resultSet);
                    var comment = CommentMapper.mapRowWithoutEmployeeId(resultSet);
                    employeeCommentsMap.computeIfAbsent(employee, comments -> new ArrayList<>()).add(comment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeCommentsMap;

    }

}
