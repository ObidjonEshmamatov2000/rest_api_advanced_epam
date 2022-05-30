package com.epam.esm.repository;

import com.epam.esm.entity.OrderEntity;

import java.util.List;
import java.util.Map;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    List<OrderEntity> getAllOrdersByUserId(Integer userId, Map<String, Integer> paginationParam);

    List<OrderEntity> getAllOrders(Map<String, Integer> paginationParam);

    List<OrderEntity> getUserOrder(Integer userID, Integer orderId);
}
