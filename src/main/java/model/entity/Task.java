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

    public Task(Task.Builder builder) {
        this.id = builder.id;
        this.taskName = builder.taskName;
        this.projectId = builder.projectId;
        this.assignedTo = builder.assignedTo;
        this.dueDate = builder.dueDate;
        this.status = builder.status;
        this.priority = builder.priority;
    }

    public static class Builder {
        private Long id;
        private String taskName;
        private Long projectId;
        private Long assignedTo;
        private LocalDateTime dueDate;
        private Status status;
        private Long priority;


        public Builder() {
        }

        public Task.Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Task.Builder setProjectId(Long projectId) {
            this.projectId = projectId;
            return this;
        }

        public Task.Builder setTaskName(String taskName) {
            this.taskName = taskName;
            return this;
        }

        public Task.Builder setAssignedTo(Long assignedTo) {
            this.assignedTo = assignedTo;
            return this;
        }

        public Task.Builder setDueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Task.Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Task.Builder setPriority(Long priority) {
            this.priority = priority;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public Long getPriority() {
        return priority;
    }
}
