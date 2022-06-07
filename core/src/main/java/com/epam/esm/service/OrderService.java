package com.epam.esm.service;

import com.epam.esm.dto.request.OrderRequestDto;
import com.epam.esm.dto.request.OrderResponseDto;
import com.epam.esm.entity.OrderEntity;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface OrderService extends BaseService<OrderEntity, OrderRequestDto> {
    OrderResponseDto findSingleUserOrder(Integer userID, Integer orderId);
}
