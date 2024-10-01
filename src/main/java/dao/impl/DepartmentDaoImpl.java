package dao.impl;

import dao.api.DepartmentDao;
import model.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    private static final Map<Long, Department> database = new HashMap<>();

    static {
        database.put(1L, new Department.Builder()
                .setId(1L)
                .setName("Human Resources")
                .build());
        database.put(2L, new Department.Builder()
                .setId(2L)
                .setName("Engineering")
                .build());
        database.put(3L, new Department.Builder()
                .setId(3L)
                .setName("Marketing")
                .build());
    }

    @Override
    public Department create(Department department) {
        if (database.containsKey(department.getId())) {
            throw new IllegalArgumentException("Key is already taken");
        }
        var newDepartment = new Department.Builder()
                .setId(department.getId())
                .setName(department.getName())
                .build();
        database.put(newDepartment.getId(), newDepartment);
        return newDepartment;
    }

    @Override
    public List<Department> findAll() {
        return List.copyOf(database.values());
    }

    @Override
    public Optional<Department> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Department update(Department department) {
        var existingDepartment = database.get(department.getId());
        if (existingDepartment != null) {
            var updateDepartment = new Department.Builder()
                    .setId(existingDepartment.getId())
                    .setName(department.getName())
                    .build();
            database.put(updateDepartment.getId(), updateDepartment);
            return updateDepartment;
        }
        throw new IllegalArgumentException("Not found department " + department.getId());
    }

    @Override
    public boolean delete(Long id) {
        return database.remove(id) != null;
    }

}
