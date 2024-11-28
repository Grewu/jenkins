package ru.senla.model.dto.request;

import jakarta.validation.constraints.NotNull;
import ru.senla.model.entity.enums.PositionType;

/**
 * DTO for creating a position request.
 *
 * <p>This class contains information about a position, specifically its type.
 */
public record PositionRequest(
    @NotNull(message = "Position name cannot be null") PositionType name) {}
