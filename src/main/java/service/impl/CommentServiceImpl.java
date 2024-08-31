package service.impl;

import dao.api.CommentDao;
import mapper.CommentMapper;
import model.dto.request.CommentRequest;
import model.dto.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.api.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, CommentMapper commentMapper) {
        this.commentDao = commentDao;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentResponse create(CommentRequest commentRequest) {
        var comment = commentMapper.toComment(commentRequest);
        return commentMapper.toCommentResponse(commentDao.create(comment));
    }

    @Override
    public List<CommentResponse> getAll() {
        var comments = commentDao.findAll();
        return commentMapper.toListOfCommentResponse(comments);
    }

    @Override
    public CommentResponse getById(Long id) {
        return commentDao.findById(id)
                .map(commentMapper::toCommentResponse)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id " + id));
    }

    @Override
    public CommentResponse update(CommentRequest commentRequest) {
        var comment = commentMapper.toComment(commentRequest);
        return commentMapper.toCommentResponse(commentDao.update(comment));
    }

    @Override
    public boolean delete(Long id) {
        return commentDao.delete(id);
    }

}
