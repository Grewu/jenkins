package model.entity;

import java.time.LocalDateTime;

public class Comment {
    private final Long id;
    private final Long taskId;
    private final Long employeeId;
    private final String commentText;
    private final LocalDateTime createdAt;

    public Comment(Long id, Long taskId, Long employeeId, String commentText, LocalDateTime createdAt) {
        this.id = id;
        this.taskId = taskId;
        this.employeeId = employeeId;
        this.commentText = commentText;
        this.createdAt = createdAt;
    }
}
