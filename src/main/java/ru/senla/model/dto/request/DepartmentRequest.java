package ru.senla.model.dto.request;

import jakarta.validation.constraints.NotNull;
import ru.senla.model.entity.enums.DepartmentType;

/**
 * DTO for creating a department request.
 *
 * <p>This class contains information about a department, specifically its type.
 */
public record DepartmentRequest(
    @NotNull(message = "Department name cannot be null") DepartmentType name) {}
