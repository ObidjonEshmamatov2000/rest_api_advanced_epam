package com.epam.esm.repository.impl;

import com.epam.esm.entity.OrderEntity;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderEntity create(OrderEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public List<OrderEntity> getAll(int limit, int offset) {
        return null;
    }

    @Override
    public OrderEntity findById(Long aLong) {
        return entityManager.find(OrderEntity.class, aLong);
    }

    @Override
    public OrderEntity update(OrderEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public int delete(Long aLong) {
        return entityManager.createQuery("delete from OrderEntity where id = :id")
                .setParameter("id", aLong)
                .executeUpdate();
    }
}
