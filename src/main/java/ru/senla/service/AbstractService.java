package ru.senla.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AbstractService<K, Q, S> {

    S create(Q t);


    Page<S> getAll(Pageable pageable);


    S getById(K id);


    S update(K id, Q t);


    void delete(K id);
}
