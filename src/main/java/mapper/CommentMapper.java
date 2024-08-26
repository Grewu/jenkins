package mapper;

import model.dto.request.CommentRequest;
import model.dto.response.CommentResponse;
import model.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public Comment toComment(CommentRequest commentRequest) {
        return new Comment.Builder()
                .setId(commentRequest.id())
                .setTaskId(commentRequest.taskId())
                .setEmployeeId(commentRequest.employeeId())
                .setCommentText(commentRequest.commentText())
                .setCreatedAt(commentRequest.createdAt())
                .build();
    }

    public CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getCommentText(),
                comment.getCreatedAt()
        );
    }

    public List<CommentResponse> toListOfCommentResponse(List<Comment> comments) {
        return comments.stream()
                .map(this::toCommentResponse)
                .collect(Collectors.toList());
    }


}
