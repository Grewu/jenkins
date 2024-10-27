package ru.senla.repository.api;

import org.springframework.stereotype.Repository;
import ru.senla.model.entity.User;
import ru.senla.repository.AbstractRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends AbstractRepository<Long, User> {
    Optional<User> findByEmail(String email);
}
