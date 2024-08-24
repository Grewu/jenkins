package dao.impl;

import dao.api.CommentDao;
import model.entity.Comment;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CommentDaoImpl implements CommentDao {

    private static final Map<Long, Comment> database = new HashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong(0);

    static {
        database.put(1L, new Comment.Builder()
                .setId(1L)
                .setTaskId(1L)
                .setEmployeeId(2L)
                .setCommentText("Initial schema design completed.")
                .setCreatedAt(LocalDateTime.of(2024, 1, 15, 10, 0))
                .build());
        database.put(2L, new Comment.Builder()
                .setId(2L)
                .setTaskId(1L)
                .setEmployeeId(2L)
                .setCommentText("Reviewed schema with the team.")
                .setCreatedAt(LocalDateTime.of(2024, 1, 20, 14, 0))
                .build());
        database.put(3L, new Comment.Builder()
                .setId(3L)
                .setTaskId(2L)
                .setEmployeeId(2L)
                .setCommentText("Started working on API endpoints.")
                .setCreatedAt(LocalDateTime.of(2024, 3, 1, 9, 0))
                .build());
        database.put(4L, new Comment.Builder()
                .setId(4L)
                .setTaskId(3L)
                .setEmployeeId(4L)
                .setCommentText("Draft marketing plan ready for review.")
                .setCreatedAt(LocalDateTime.of(2024, 2, 15, 11, 30))
                .build());
    }


    @Override
    public Comment create(Comment comment) {
        var id = idGenerator.incrementAndGet();
        return database.put(comment.getId(),
                new Comment.Builder()
                        .setId(id)
                        .setTaskId(comment.getTaskId())
                        .setEmployeeId(comment.getEmployeeId())
                        .setCommentText(comment.getCommentText())
                        .setCreatedAt(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public List<Comment> findAll() {
        return List.copyOf(database.values());
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Comment update(Comment comment) {
        var existingComment = database.get(comment.getId());
        if (existingComment != null) {
            var updateComment = new Comment.Builder()
                    .setId(existingComment.getId())
                    .setTaskId(comment.getTaskId())
                    .setEmployeeId(comment.getEmployeeId())
                    .setCommentText(comment.getCommentText())
                    .setCreatedAt(existingComment.getCreatedAt())
                    .build();
            database.put(updateComment.getId(), updateComment);
            return updateComment;
        }
        throw new IllegalArgumentException("Not found comment " + comment.getId());
    }

    @Override
    public boolean delete(Long id) {
        return database.remove(id) != null;
    }
}
