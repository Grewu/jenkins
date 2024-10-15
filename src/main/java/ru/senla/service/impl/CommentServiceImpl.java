package ru.senla.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.mapper.CommentMapper;
import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.model.entity.Comment;
import ru.senla.model.entity.UserProfile;
import ru.senla.repository.api.CommentRepository;
import ru.senla.service.api.CommentService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    @Override
    @Transactional
    public CommentResponse create(CommentRequest commentRequest) {
        var comment = commentMapper.toComment(commentRequest);
        return commentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @Override
    public Page<CommentResponse> getAll(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(commentMapper::toCommentResponse);
    }

    @Override
    public CommentResponse getById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toCommentResponse)
                .orElseThrow(() -> new EntityNotFoundException(Comment.class, id));
    }

    @Override
    @Transactional
    public CommentResponse update(Long id, CommentRequest commentRequest) {
        return commentRepository.findById(id)
                .map(current -> commentMapper.update(commentRequest, current))
                .map(commentRepository::save)
                .map(commentMapper::toCommentResponse)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

}
