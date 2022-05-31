package com.epam.esm.service;

import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

public interface BaseService<T, G> {
    T create(G g, BindingResult bindingResult);

    T findById(Long id);

    void deleteById(Long id);

    List<T> findAll(Map<String, Object> params);
}
