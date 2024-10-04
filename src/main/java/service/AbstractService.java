package service;

import java.util.List;

public interface AbstractService<K, Q, S> {

    S create(Q t);


    List<S> getAll();


    S getById(K id);


    S update(K id, Q t);


    void delete(Q t);
}
