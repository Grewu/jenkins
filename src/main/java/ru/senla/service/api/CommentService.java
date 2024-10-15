package ru.senla.service.api;

import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.service.AbstractService;

public interface CommentService extends AbstractService<Long, CommentRequest, CommentResponse> {
}
