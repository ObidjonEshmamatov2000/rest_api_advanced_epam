package com.epam.esm.repository;

import java.util.List;
import java.util.Map;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 * @param <T> 'T' is Entity type for CRUD operations
 * @param <Id> Primary key of T (object)
 */

public interface CrudRepository<T, Id>{
    /**
     * Creates a new instance of an entity in the database.
     * @param obj Object to create
     * @return optional id of created object
     */
    T merge(T obj);

    /**
     * Gets all existing entities with provided type and provided limit and offset.
     * @param paginationParam limit and offset of entities
     * @return list of entities
     */
    List<T> findAll(Map<String, Integer> paginationParam);

    /**
     * Gets entity with the provided id.
     * @param id id of the needed object
     * @return optional object of provided type
     */
    T findById(Id id);

    /**
     * Deletes entity with the provided id.
     * @param id id of the object to be deleted
     * @return the number of rows affected
     */
    int deleteById(Id id);
}
