package dao.impl;

import dao.api.CommentDao;
import model.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDaoImpl extends AbstractDaoImpl<Long, Comment> implements CommentDao {

}
