package org.simbirsoft.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E,ID> {
    void save(E entity);

    Optional<E> findById(ID id);

    List<E> findAll(Integer offset);

    void update(E entity);

    void deleteById(ID id);
}
