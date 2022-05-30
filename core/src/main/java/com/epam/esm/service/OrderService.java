package com.epam.esm.service;

import com.epam.esm.dto.OrderRequestDto;
import com.epam.esm.dto.OrderResponseDto;
import com.epam.esm.entity.OrderEntity;

import java.util.List;

public interface OrderService extends BaseService<OrderEntity, OrderRequestDto> {
    OrderResponseDto getUserOrder(Integer userID, Integer orderId);
}
