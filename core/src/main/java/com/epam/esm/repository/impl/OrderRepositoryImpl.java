package com.epam.esm.repository.impl;

import com.epam.esm.entity.OrderEntity;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public OrderEntity create(OrderEntity obj) {
        return null;
    }

    @Override
    public List<OrderEntity> getAll(int limit, int offset) {
        return null;
    }

    @Override
    public OrderEntity findById(Long aLong) {
        return null;
    }

    @Override
    public OrderEntity update(OrderEntity obj) {
        return null;
    }

    @Override
    public int delete(Long aLong) {
        return 0;
    }
}
