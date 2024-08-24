package dao.impl;

import dao.api.TaskDao;
import model.entity.Task;
import model.entity.enums.Status;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskDaoImpl implements TaskDao {

    private static final Map<Long, Task> database = new HashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(0);

    static {
        database.put(1L, new Task.Builder()
                .setId(1L)
                .setTaskName("Design Database Schema")
                .setProjectId(1L)
                .setAssignedTo(2L)
                .setDueDate(LocalDateTime.of(2024, 3, 1, 0, 0))
                .setStatus(Status.IN_PROGRESS)
                .setPriority(1L)
                .build());

        database.put(2L, new Task.Builder()
                .setId(2L)
                .setTaskName("Develop API Endpoints")
                .setProjectId(1L)
                .setAssignedTo(2L)
                .setDueDate(LocalDateTime.of(2024, 6, 1, 0, 0))
                .setStatus(Status.NOT_STARTED)
                .setPriority(2L)
                .build());

        database.put(3L, new Task.Builder()
                .setId(3L)
                .setTaskName("Create Marketing Plan")
                .setProjectId(2L)
                .setAssignedTo(4L)
                .setDueDate(LocalDateTime.of(2024, 4, 1, 0, 0))
                .setStatus(Status.IN_PROGRESS)
                .setPriority(1L)
                .build());

        database.put(4L, new Task.Builder()
                .setId(4L)
                .setTaskName("Conduct Sales Training")
                .setProjectId(2L)
                .setAssignedTo(3L)
                .setDueDate(LocalDateTime.of(2024, 5, 1, 0, 0))
                .setStatus(Status.COMPLETED)
                .setPriority(3L)
                .build());
    }

    @Override
    public Task create(Task task) {
        var id = idGenerator.incrementAndGet();
        return database.put(task.getId(),
                new Task.Builder()
                        .setId(id)
                        .setTaskName(task.getTaskName())
                        .setProjectId(task.getProjectId())
                        .setAssignedTo(task.getAssignedTo())
                        .setDueDate(task.getDueDate())
                        .setStatus(task.getStatus())
                        .setPriority(task.getPriority())
                        .build()
        );
    }

    @Override
    public List<Task> findAll() {
        return List.copyOf(database.values());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Task update(Task task) {
        var existingTask = database.get(task.getId());
        if (existingTask != null) {
            var updateTask = new Task.Builder()
                    .setId(task.getId())
                    .setTaskName(task.getTaskName())
                    .setProjectId(task.getProjectId())
                    .setAssignedTo(task.getAssignedTo())
                    .setDueDate(task.getDueDate())
                    .setStatus(task.getStatus())
                    .setPriority(task.getPriority())
                    .build();
            database.put(existingTask.getId(), existingTask);
            return updateTask;
        }
        throw new IllegalArgumentException("Not found task " + task.getId());
    }

    @Override
    public boolean delete(Long id) {
        return database.remove(id) != null;
    }

}
