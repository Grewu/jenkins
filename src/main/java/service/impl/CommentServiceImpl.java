package service.impl;

import dao.api.CommentDao;
import exception.EntityNotFoundException;
import mapper.CommentMapper;
import model.dto.request.CommentRequest;
import model.dto.response.CommentResponse;
import model.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.api.CommentService;

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public CommentResponse create(CommentRequest commentRequest) {
        var comment = commentMapper.toComment(commentRequest);
        return commentMapper.toCommentResponse(commentDao.create(comment));
    }

    @Override
    public List<CommentResponse> getAll() {
        return commentDao.findAll().stream()
                .map(commentMapper::toCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse getById(Long id) {
        return commentDao.findById(id)
                .map(commentMapper::toCommentResponse)
                .orElseThrow(() -> new EntityNotFoundException(Comment.class, id));
    }

    @Override
    @Transactional
    public CommentResponse update(Long id, CommentRequest commentRequest) {
        var comment = commentMapper.toComment(commentRequest);
        return commentMapper.toCommentResponse(commentDao.update(comment));
    }

    @Override
    @Transactional
    public void delete(CommentRequest commentRequest) {
        var comment = commentMapper.toComment(commentRequest);
        commentDao.delete(comment);
    }

}
