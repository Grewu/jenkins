package ru.senla.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.senla.model.entity.Comment;
import ru.senla.repository.AbstractRepository;

@Repository
public interface CommentRepository extends AbstractRepository<Long, Comment> {
  @Query(
      """
            SELECT new ru.senla.model.entity.Comment(
               c.id,
               c.task,
               c.usersProfile,
               c.commentText,
               c.createdAt
            )
            FROM Comment c
            WHERE c.usersProfile.id = :userProfileId
            """)
  Page<Comment> findCommentsByProfileId(
      @Param("userProfileId") Long userProfileId, Pageable pageable);
}
