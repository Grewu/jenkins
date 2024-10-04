package controller;

import lombok.RequiredArgsConstructor;
import model.dto.request.CommentRequest;
import model.dto.response.CommentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.api.CommentService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = CommentController.COMMENT_API_PATH)
public class CommentController {

    private final CommentService commentService;
    public static final String COMMENT_API_PATH = "/api/v0/comments";


    @PostMapping
    //TODO:DTO taskID?
    public ResponseEntity<CommentResponse> create(@RequestBody CommentRequest commentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.create(commentRequest));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> update(@PathVariable Long id,
                                                  @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.update(id, commentRequest));
    }


    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody CommentRequest commentRequest) {
        commentService.delete(commentRequest);
        return ResponseEntity.noContent().build();
    }

}
