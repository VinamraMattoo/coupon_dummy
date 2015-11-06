package com.portea.dao;

import java.util.List;

/**
 * The base interface for all DAO classes. This interface provides common methods for all DAOs
 *
 * @param <ID> The identifier for an entity
 * @param <E> The entity to be managed by the DAO
 */
public interface Dao<ID, E> {

    E create(E entity);

    void delete(E entity);

    void delete(List<E> entities);

    void deleteById(ID id);

    E find(ID id);

    E update(E entity);

    E refresh(E entity);
}
