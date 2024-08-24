package dao.impl;

import dao.api.EmployeeDao;
import model.entity.Employee;
import model.entity.enums.Position;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private static final Map<Long, Employee> database = new HashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(0);

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
        var id = idGenerator.incrementAndGet();
        return database.put(employee.getId(),
                new Employee.Builder()
                        .setId(id)
                        .setFirstName(employee.getFirstName())
                        .setLastName(employee.getLastName())
                        .setDepartmentId(employee.getDepartmentId())
                        .setEmail(employee.getEmail())
                        .setPassword(employee.getPassword())
                        .setPosition(employee.getPosition())
                        .build()
        );
    }

    @Override
    public List<Employee> findAll() {
        return List.copyOf(database.values());
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Employee update(Employee employee) {
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
        return database.remove(id) != null;
    }

}
