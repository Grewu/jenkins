package ru.senla.model.dto.request;

import jakarta.validation.constraints.NotNull;
import ru.senla.model.entity.enums.DepartmentType;

public record DepartmentRequest(
        @NotNull(message = "Department name cannot be null")
        DepartmentType name
) {
}
