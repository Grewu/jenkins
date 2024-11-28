package ru.senla.repository.api;

import org.springframework.stereotype.Repository;
import ru.senla.model.entity.Position;
import ru.senla.model.entity.enums.PositionType;
import ru.senla.repository.AbstractRepository;

@Repository
public interface PositionRepository extends AbstractRepository<Long, Position> {
  boolean existsByName(PositionType name);
}
