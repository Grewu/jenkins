package model.entity;

import java.time.LocalDateTime;

public class Comment {
    private final Long id;
    private final Long taskId;
    private final Long employeeId;
    private final String commentText;
    private final LocalDateTime createdAt;

    private Comment(Builder builder) {
        this.id = builder.id;
        this.taskId = builder.taskId;
        this.employeeId = builder.employeeId;
        this.commentText = builder.commentText;
        this.createdAt = builder.createdAt;
    }

    public static class Builder {
        private Long id;
        private Long taskId;
        private Long employeeId;
        private String commentText;
        private LocalDateTime createdAt;

        public Builder() {
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setTaskId(Long taskId) {
            this.taskId = taskId;
            return this;
        }

        public Builder setEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder setCommentText(String commentText) {
            this.commentText = commentText;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

    public Long getId() {
        return id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getCommentText() {
        return commentText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", employeeId=" + employeeId +
                ", commentText='" + commentText + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }


}
