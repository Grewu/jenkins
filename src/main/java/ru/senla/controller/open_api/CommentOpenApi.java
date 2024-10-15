package ru.senla.controller.open_api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;

import java.util.List;

@Tag(name = "Comment", description = "API for working with comments")
public interface CommentOpenApi {

    ResponseEntity<CommentResponse> create(@RequestBody CommentRequest commentRequest);

    ResponseEntity<List<CommentResponse>> getAll();

    ResponseEntity<CommentResponse> getById(@PathVariable Long id);

    ResponseEntity<CommentResponse> update(@PathVariable Long id, @RequestBody CommentRequest commentRequest);

    ResponseEntity<Void> delete(@PathVariable Long id);
}
