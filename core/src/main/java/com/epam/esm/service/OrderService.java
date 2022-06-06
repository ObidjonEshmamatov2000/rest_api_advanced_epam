package com.epam.esm.service;

import com.epam.esm.dto.OrderRequestDto;
import com.epam.esm.dto.OrderResponseDto;
import com.epam.esm.entity.OrderEntity;

import java.util.List;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface OrderService extends BaseService<OrderEntity, OrderRequestDto> {
    OrderResponseDto findSingleUserOrder(Integer userID, Integer orderId);
}
