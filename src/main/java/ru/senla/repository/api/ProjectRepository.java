package ru.senla.repository.api;

import org.springframework.stereotype.Repository;
import ru.senla.repository.AbstractRepository;
import ru.senla.model.entity.Project;
@Repository
public interface ProjectRepository extends AbstractRepository<Long, Project> {
}
