package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.PositionRequest;
import ru.senla.model.dto.response.PositionResponse;
import ru.senla.model.entity.Position;

@Mapper(componentModel = "spring")
public interface PositionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Position toPosition(PositionRequest positionRequest);

    PositionResponse toPositionResponse(Position position);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    Position update(PositionRequest positionRequest, @MappingTarget Position current);
}
