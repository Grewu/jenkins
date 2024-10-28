package ru.senla.model.dto.response;

/**
 * DTO for a department response.
 * <p>
 * This class contains the details of a department, including its unique identifier and name.
 * </p>
 */
public record DepartmentResponse(
        Long id,
        String name
) {
}
