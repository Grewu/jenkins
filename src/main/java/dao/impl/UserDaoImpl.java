package dao.impl;

import dao.api.UserDao;
import model.entity.User;
import model.entity.User_;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl extends AbstractDaoImpl<Long, User> implements UserDao {

    @Override
    public Optional<User> findByEmail(String email) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(User.class);
        var root = criteriaQuery.from(User.class);

        var query = criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get(User_.EMAIL), email));

        return entityManager.createQuery(query)
                .getResultStream()
                .findFirst();
    }
}
