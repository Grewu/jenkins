package ru.senla.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * DTO for a project response.
 *
 * <p>This class encapsulates the details of a project, including its unique identifier, name,
 * project code, description, start and end dates, and the ID of the owner.
 */
public record ProjectResponse(
    Long id,
    String name,
    String projectCode,
    String description,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime endDate,
    Long owner) {}
