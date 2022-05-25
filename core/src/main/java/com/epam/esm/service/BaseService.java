package com.epam.esm.service;

import org.springframework.validation.BindingResult;

public interface BaseService<T, G> {
    T create(G g, BindingResult bindingResult);
    T get(Long id);
    void delete(Long id);
}
