package ru.senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.annotation.Logging;
import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.service.api.CommentService;

@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = CommentRestController.COMMENT_API_PATH)
//TODO: OPEN API
public class CommentRestController {

    private final CommentService commentService;
    protected static final String COMMENT_API_PATH = "/api/v0/comments";


    @PostMapping
    @PreAuthorize("hasAuthority('comments:write')")
    public ResponseEntity<CommentResponse> create(@Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.create(commentRequest));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('comments:read')")
    public ResponseEntity<Page<CommentResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.getAll(pageable));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('comments:read')")
    public ResponseEntity<CommentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('comments:write')")
    public ResponseEntity<CommentResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentService.update(id, commentRequest));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('comments:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
