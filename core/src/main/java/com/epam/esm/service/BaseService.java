package com.epam.esm.service;

public interface BaseService<T, G> {
    T create(G g);
    T get(Long id);
    void delete(Long id);
}
