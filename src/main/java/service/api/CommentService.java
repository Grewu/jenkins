package service.api;

import model.dto.request.CommentRequest;
import model.dto.response.CommentResponse;
import service.AbstractService;

public interface CommentService extends AbstractService<Long, CommentRequest, CommentResponse> {
}
