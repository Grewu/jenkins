package ru.senla.service.api;

import ru.senla.model.dto.request.PositionRequest;
import ru.senla.model.dto.response.PositionResponse;
import ru.senla.service.AbstractService;

/**
 * Service interface for managing positions within the application.
 *
 * <p>This interface defines the operations for creating, retrieving, updating, and deleting
 * position records. It extends the abstract service interface to provide a consistent way to manage
 * positions.
 */
public interface PositionService extends AbstractService<Long, PositionRequest, PositionResponse> {}
