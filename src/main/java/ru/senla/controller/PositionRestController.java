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

@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PositionRestController.POSITION_API_PATH)
public class PositionRestController {

    protected static final String POSITION_API_PATH = "/api/v0/positions";
    private final PositionService positionService;


    @PostMapping
    @PreAuthorize("hasAuthority('position:write')")
    public ResponseEntity<PositionResponse> create(@Valid @RequestBody PositionRequest positionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionService.create(positionRequest));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('position:read')")
    public ResponseEntity<Page<PositionResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionService.getAll(pageable));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('position:read')")
    public ResponseEntity<PositionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('position:write')")
    public ResponseEntity<PositionResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody PositionRequest positionRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(positionService.update(id, positionRequest));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('position:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
