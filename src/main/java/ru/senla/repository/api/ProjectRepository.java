package ru.senla.repository.api;

import org.springframework.stereotype.Repository;
import ru.senla.model.entity.Project;
import ru.senla.repository.AbstractRepository;

@Repository
public interface ProjectRepository extends AbstractRepository<Long, Project> {}
