package ru.senla.repository.api;

import org.springframework.stereotype.Repository;
import ru.senla.repository.AbstractRepository;
import ru.senla.model.entity.Department;
@Repository
public interface DepartmentRepository extends AbstractRepository<Long, Department> {
}
