package ru.senla.repository.api;

import org.springframework.stereotype.Repository;
import ru.senla.model.entity.Comment;
import ru.senla.repository.AbstractRepository;

@Repository
public interface CommentRepository extends AbstractRepository<Long, Comment> {
}
