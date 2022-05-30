package com.epam.esm.repository.impl;

import com.epam.esm.entity.OrderEntity;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.utils.ParamsStringProvider;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.*;

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

    @Override
    public List<OrderEntity> getAllOrdersByUserId(Integer userId, Map<String, Integer> paginationParam) {
        String query = "select o from OrderEntity o join fetch o.user u where u.id = ?1";
        return entityManager.createQuery(
                        query,
                        OrderEntity.class
                )
                .setParameter(1, Long.valueOf(userId))
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public List<OrderEntity> getAllOrders(Map<String, Integer> paginationParam) {
        String query = "select o from OrderEntity o";
        return entityManager.createQuery(
                query,
                OrderEntity.class
        )
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public List<OrderEntity> getUserOrder(Integer userID, Integer orderId) {
//        String query2 = "select o from OrderEntity o where o.id = ?1";
        String query = "select o from OrderEntity o join fetch o.user u where o.id = ?1 and u.id = ?2";
        return entityManager.createQuery(
                query,
                OrderEntity.class
        )
                .setParameter(1, Long.valueOf(orderId))
                .setParameter(2, Long.valueOf(userID))
                .getResultList();
    }
}
