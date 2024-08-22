package model.entity;

import model.entity.enums.Status;

import java.time.LocalDateTime;

public class Task {
    private final Long id;
    private final String taskName;
    private final Long projectId;
    private final Long assignedTo;
    private final LocalDateTime dueDate;
    private final Status status;
    private final Long priority;

    public Task(Long id, String taskName, Long projectId, Long assignedTo, LocalDateTime dueDate, Status status, Long priority) {
        this.id = id;
        this.taskName = taskName;
        this.projectId = projectId;
        this.assignedTo = assignedTo;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }
}
