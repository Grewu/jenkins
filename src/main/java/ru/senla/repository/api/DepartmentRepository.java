package ru.senla.repository.api;

import org.springframework.stereotype.Repository;
import ru.senla.model.entity.Department;
import ru.senla.model.entity.enums.DepartmentType;
import ru.senla.repository.AbstractRepository;

@Repository
public interface DepartmentRepository extends AbstractRepository<Long, Department> {
  boolean existsByName(DepartmentType name);
}
