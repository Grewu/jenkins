package mapper;

import model.dto.request.CommentRequest;
import model.dto.response.CommentResponse;
import model.entity.Comment;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static Comment mapRowWithoutEmployeeId(ResultSet resultSet) throws SQLException {
        return new Comment.Builder()
                .setId(resultSet.getLong("id"))
                .setTaskId(resultSet.getLong("task_id"))
                .setCommentText(resultSet.getString("comment_text"))
                .setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                .build();
    }

    public static Comment mapRowWithoutEmployeeIdAndCreatedAtAndTaskId(ResultSet resultSet) throws SQLException {
        return new Comment.Builder()
                .setId(resultSet.getLong("id"))
                .setCommentText(resultSet.getString("comment_text"))
                .build();
    }

}
