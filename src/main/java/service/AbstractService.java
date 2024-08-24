package service;

import java.util.List;

public interface AbstractService<K, Q,S> {

    S create(Q t);


    List<S> getAll();


    S getById(K id);


    S update(Q t);


    void delete(K id);
}
