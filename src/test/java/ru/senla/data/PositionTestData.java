package ru.senla.data;


import lombok.Builder;
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

}
