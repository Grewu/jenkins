package dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<K, T> {

    T create(T t);

    List<T> findAll();

    Optional<T> findById(K id);

    T update(T t);

    boolean delete(K id);
}
