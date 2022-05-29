package com.epam.esm.service;

import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface BaseService<T, G> {
    T create(G g, BindingResult bindingResult);
    T get(Long id);
    void delete(Long id);
    List<T> getAll(Map<String, Object> params);
}
