package ru.senla.data;

import lombok.Builder;
import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.model.entity.Comment;
import ru.senla.model.entity.Task;
import ru.senla.model.entity.UserProfile;

import java.time.LocalDateTime;

@Builder(setterPrefix = "with")
public class CommentTestData {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private Task task = TaskTestData.builder().build().buildTask();
    @Builder.Default
    private UserProfile userProfile = UserProfileTestData.builder().build().buildUserProfile();
    @Builder.Default
    private String commentText = "commentText";
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.parse("2024-09-30T12:00:00");

    public Comment buildComment() {
        return new Comment(id, task, userProfile, commentText, createdAt);
    }

    public CommentRequest buildCommentRequest() {
        return new CommentRequest(task.getId(), userProfile.getId(), commentText, createdAt);
    }

    public CommentResponse buildCommentResponse() {
        return new CommentResponse(id, task.getId(), userProfile.getId(), commentText, createdAt);
    }
}
