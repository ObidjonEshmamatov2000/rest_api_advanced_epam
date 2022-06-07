package com.epam.esm.repository.impl;

import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

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
    public List<OrderEntity> findAll(PaginationParams paginationParams) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> cq = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> entityRoot = cq.from(OrderEntity.class);
        cq.select(entityRoot);
        return entityManager.createQuery(cq)
                .setFirstResult(paginationParams.getPageNumber())
                .setMaxResults(paginationParams.getPageSize())
                .getResultList();
    }

    @Override
    public int deleteById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<OrderEntity> criteriaDelete = cb.createCriteriaDelete(OrderEntity.class);
        Root<OrderEntity> entityRoot = criteriaDelete.from(OrderEntity.class);
        criteriaDelete.where(cb.equal(entityRoot.get("id"), id));
        return entityManager
                .createQuery(criteriaDelete)
                .executeUpdate();
    }

    @Override
    public OrderEntity findById(Long id) {
        return entityManager.find(OrderEntity.class, id);
    }

    @Override
    public List<OrderEntity> findAllOrdersByUserId(Integer userId, PaginationParams paginationParams) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> cq = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> entityRoot = cq.from(OrderEntity.class);
        entityRoot.fetch("user", JoinType.LEFT);
        cq.where(cb.equal(entityRoot.get("user").get("id"), userId));
        return entityManager
                .createQuery(cq)
                .setFirstResult(paginationParams.getPageNumber())
                .setMaxResults(paginationParams.getPageSize())
                .getResultList();
    }

    @Override
    public List<OrderEntity> findSingleUserOrder(Integer userId, Integer orderId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> cq = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> entityRoot = cq.from(OrderEntity.class);
        entityRoot.fetch("user", JoinType.LEFT);
        cq.where(
                cb.equal(entityRoot.get("user").get("id"), userId),
                cb.equal(entityRoot.get("id"), orderId)
        );
        return entityManager
                .createQuery(cq)
                .getResultList();
    }
}
