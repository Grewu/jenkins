package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.request.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.api.CommentService;

import java.io.IOException;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CommentController(CommentService commentService, ObjectMapper objectMapper) {
        this.commentService = commentService;
        this.objectMapper = objectMapper;
    }

    public String create(CommentRequest commentRequest) throws IOException {
        return objectMapper.writeValueAsString(commentService.create(commentRequest));
    }

    public String getAll() throws IOException {
        return objectMapper.writeValueAsString(commentService.getAll());
    }

    public String getById(Long id) throws IOException {
        return objectMapper.writeValueAsString(commentService.getById(id));
    }

    public String update(CommentRequest commentRequest) throws IOException {
        return objectMapper.writeValueAsString(commentService.update(commentRequest));
    }

    public void delete(Long id) {
        commentService.delete(id);
    }

}
