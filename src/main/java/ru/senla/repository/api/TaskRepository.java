package ru.senla.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.senla.model.entity.Task;
import ru.senla.repository.AbstractRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends AbstractRepository<Long, Task>, JpaSpecificationExecutor<Task> {
    @Query("""
            SELECT new ru.senla.model.entity.Task(
                t.id,
                t.name,
                t.project,
                t.assignedTo,
                t.createdBy,
                t.dueDate,
                t.status,
                t.priority)
            FROM Task t
            WHERE t.project.id = :projectId
            """)
    Page<Task> findTasksByProjectId(@Param("projectId") Long projectId, Pageable pageable);


    @Query("""
            SELECT t FROM Task t
            WHERE t.dueDate BETWEEN :start AND :end
            """)
    List<Task> findAllByDueDateBetween(LocalDateTime start, LocalDateTime end);

}
