package com.epam.esm.service;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface BaseService<T, G> {
    T create(G g);

    T findById(Long id);

    void deleteById(Long id);
}
