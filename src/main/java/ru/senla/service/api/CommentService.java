package ru.senla.service.api;

import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.service.AbstractService;

/**
 * Service interface for managing comments within the application.
 * <p>
 * This interface defines the operations for creating, retrieving, updating, and deleting
 * comment records. It extends the abstract service interface to provide a consistent
 * way to manage comments associated with tasks.
 * </p>
 */
public interface CommentService extends AbstractService<Long, CommentRequest, CommentResponse> {
}
