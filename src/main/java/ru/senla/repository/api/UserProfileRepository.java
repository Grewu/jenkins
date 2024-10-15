package ru.senla.repository.api;

import org.springframework.stereotype.Repository;
import ru.senla.repository.AbstractRepository;
import ru.senla.model.entity.UserProfile;

@Repository
public interface UserProfileRepository extends AbstractRepository<Long, UserProfile> {

}
