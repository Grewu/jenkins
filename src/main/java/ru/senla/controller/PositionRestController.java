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
import ru.senla.model.dto.request.PositionRequest;
import ru.senla.model.dto.response.PositionResponse;
import ru.senla.service.api.PositionService;

/**
 * The PositionRestController class provides REST endpoints for managing positions within the system.
 * This controller allows clients to create, retrieve, update, and delete position entities.
 * Each endpoint has specific authorization requirements and supports JSON responses.
 * It uses {@link PositionService} to handle the business logic related to position management.
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li><b>POST /api/v0/positions</b>: Creates a new position.</li>
 *     <li><b>GET /api/v0/positions</b>: Retrieves a paginated list of all positions.</li>
 *     <li><b>GET /api/v0/positions/{id}</b>: Retrieves a specific position by its ID.</li>
 *     <li><b>PUT /api/v0/positions/{id}</b>: Updates a specific position by its ID.</li>
 *     <li><b>DELETE /api/v0/positions/{id}</b>: Deletes a specific position by its ID.</li>
 * </ul>
 *
 * <p>Authorization Requirements:</p>
 * <ul>
 *     <li><b>position:write</b> permission is required for creating and updating positions.</li>
 *     <li><b>position:read</b> permission is required for retrieving positions.</li>
 *     <li><b>position:delete</b> permission is required for deleting positions.</li>
 * </ul>
 *
 * @see PositionRequest
 * @see PositionResponse
 * @see PositionService
 */
@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PositionRestController.POSITION_API_PATH)
public class PositionRestController {

    protected static final String POSITION_API_PATH = "/api/v0/positions";
    private final PositionService positionService;

    /**
     * Creates a new position.
     *
     * @param positionRequest the {@link PositionRequest} object containing details of the position to create.
     * @return a {@link ResponseEntity} containing the created {@link PositionResponse} with HTTP status 201 (Created).
     */
    @PostMapping
    @PreAuthorize("hasAuthority('position:write')")
    public ResponseEntity<PositionResponse> create(@Valid @RequestBody PositionRequest positionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionService.create(positionRequest));
    }

    /**
     * Retrieves a paginated list of all positions.
     *
     * @param pageable the pagination information, with a default page size of 20.
     * @return a {@link ResponseEntity} containing a {@link Page} of {@link PositionResponse} objects with HTTP status 200 (OK).
     */
    @GetMapping
    @PreAuthorize("hasAuthority('position:read')")
    public ResponseEntity<Page<PositionResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionService.getAll(pageable));
    }

    /**
     * Retrieves a specific position by its ID.
     *
     * @param id the ID of the position to retrieve.
     * @return a {@link ResponseEntity} containing the {@link PositionResponse} with HTTP status 200 (OK).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('position:read')")
    public ResponseEntity<PositionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionService.getById(id));
    }

    /**
     * Updates an existing position with the specified ID.
     *
     * @param id the ID of the position to update.
     * @param positionRequest the {@link PositionRequest} object containing updated details of the position.
     * @return a {@link ResponseEntity} containing the updated {@link PositionResponse} with HTTP status 200 (OK).
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('position:write')")
    public ResponseEntity<PositionResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody PositionRequest positionRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionService.update(id, positionRequest));
    }

    /**
     * Deletes a position with the specified ID.
     *
     * @param id the ID of the position to delete.
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content) indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('position:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}