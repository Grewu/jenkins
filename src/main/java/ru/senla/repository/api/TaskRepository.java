package ru.senla.repository.api;

import org.springframework.stereotype.Repository;
import ru.senla.repository.AbstractRepository;
import ru.senla.model.entity.Task;
@Repository
public interface TaskRepository extends AbstractRepository<Long, Task> {
}
