package com.epam.esm.repository;

import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.entity.OrderEntity;

import java.util.List;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    List<OrderEntity> findAllOrdersByUserId(Integer userId, PaginationParams paginationParams);

    List<OrderEntity> findSingleUserOrder(Integer userID, Integer orderId);
}
