package com.epam.esm.repository.impl;

import com.epam.esm.entity.OrderEntity;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.LIMIT;
import static com.epam.esm.utils.ParamsStringProvider.OFFSET;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */


@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderEntity merge(OrderEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public List<OrderEntity> findAll(Map<String, Integer> paginationParam) {
        String query = "select o from OrderEntity o";
        return entityManager
                .createQuery(query,OrderEntity.class)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public int deleteById(Long aLong) {
        return entityManager
                .createQuery("delete from OrderEntity where id = :id")
                .setParameter("id", aLong)
                .executeUpdate();
    }

    @Override
    public OrderEntity findById(Long aLong) {
        return entityManager.find(OrderEntity.class, aLong);
    }

    @Override
    public List<OrderEntity> findAllOrdersByUserId(Integer userId, Map<String, Integer> paginationParam) {
        String query = "select o from OrderEntity o join fetch o.user u where u.id = ?1";
        return entityManager
                .createQuery(query, OrderEntity.class)
                .setParameter(1, Long.valueOf(userId))
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public List<OrderEntity> findSingleUserOrder(Integer userID, Integer orderId) {
        String query = "select o from OrderEntity o join fetch o.user u where o.id = ?1 and u.id = ?2";
        return entityManager
                .createQuery(query, OrderEntity.class)
                .setParameter(1, Long.valueOf(orderId))
                .setParameter(2, Long.valueOf(userID))
                .getResultList();
    }
}
