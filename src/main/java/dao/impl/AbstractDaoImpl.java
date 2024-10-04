package dao.impl;

import dao.AbstractDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;


public class AbstractDaoImpl<K extends Serializable, T> implements AbstractDao<K, T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    protected AbstractDaoImpl() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Override
    public T create(T t) {
        entityManager.persist(t);
        return t;
    }

    @Override
    public List<T> findAll() {
        var criteriaBuilder = this.entityManager.getCriteriaBuilder();

        var criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
        var root = criteriaQuery.from(this.entityClass);
        criteriaQuery.select(root);

        var query = this.entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Optional<T> findById(K id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T update(T t) {
        return entityManager.merge(t);
    }


    @Override
    public void delete(T t) {
        entityManager.remove(t);
    }
}
