package ru.senla.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.entity.Project;
import ru.senla.model.entity.TaskHistory;
import ru.senla.repository.AbstractRepository;

@Repository
public interface TaskHistoryRepository extends AbstractRepository<Long, TaskHistory> {
    @Query("""
            SELECT new ru.senla.model.entity.TaskHistory(
                th.id,
                th.task,
                th.name,
                th.project,
                th.assignedTo,
                th.createdBy,
                th.dueDate,
                th.status,
                th.priority,
                th.changedBy,
                th.changedDate,
                th.changedDescription
            )
            FROM TaskHistory th
            WHERE th.task.id = :taskId
            """)
    Page<TaskHistory> findTaskHistoryByTaskId(@Param("taskId") Long taskId, Pageable pageable);
}
