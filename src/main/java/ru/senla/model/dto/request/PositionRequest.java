package ru.senla.model.dto.request;

import jakarta.validation.constraints.NotNull;
import ru.senla.model.entity.enums.PositionType;

public record PositionRequest(
        @NotNull(message = " Position name cannot be null")
        PositionType name
) {
}
