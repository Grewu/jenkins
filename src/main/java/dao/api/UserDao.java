package dao.api;

import dao.AbstractDao;
import model.entity.User;

import java.util.Optional;

public interface UserDao extends AbstractDao<Long, User> {
    Optional<User> findByEmail(String email);
}
