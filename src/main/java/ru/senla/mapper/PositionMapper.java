package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.PositionRequest;
import ru.senla.model.dto.response.PositionResponse;
import ru.senla.model.entity.Position;

/**
 * Mapper interface for converting between {@link Position} entities and their
 * corresponding DTOs, {@link PositionRequest} and {@link PositionResponse}.
 *
 * <p>
 * This interface uses MapStruct to generate the implementation for mapping
 * properties between the entities and DTOs. It is annotated with
 * {@code @Mapper(componentModel = "spring")} to enable Spring's component
 * scanning.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface PositionMapper {

    /**
     * Converts a {@link PositionRequest} to a {@link Position} entity.
     *
     * @param positionRequest the DTO containing the details of the position
     * @return the converted {@link Position} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Position toPosition(PositionRequest positionRequest);

    /**
     * Converts a {@link Position} entity to a {@link PositionResponse} DTO.
     *
     * @param position the position entity to be converted
     * @return the converted {@link PositionResponse} DTO
     */
    PositionResponse toPositionResponse(Position position);

    /**
     * Updates an existing {@link Position} entity with values from a
     * {@link PositionRequest}.
     *
     * @param positionRequest the DTO containing the updated position details
     * @param current         the existing {@link Position} entity to be updated
     * @return the updated {@link Position} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Position update(PositionRequest positionRequest, @MappingTarget Position current);
}
