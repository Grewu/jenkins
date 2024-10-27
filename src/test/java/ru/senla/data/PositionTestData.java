package ru.senla.data;


import lombok.Builder;
import ru.senla.model.dto.request.PositionRequest;
import ru.senla.model.dto.response.PositionResponse;
import ru.senla.model.entity.Position;
import ru.senla.model.entity.enums.PositionType;

@Builder(setterPrefix = "with")
public class PositionTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private PositionType name = PositionType.DEVELOPER;

    public Position buildPosition() {
        return new Position(id, name);
    }

    public PositionRequest buildPositionRequest() {
        return new PositionRequest(name);
    }

    public PositionResponse buildPositionResponse() {
        return new PositionResponse(id, name.name());
    }
}
