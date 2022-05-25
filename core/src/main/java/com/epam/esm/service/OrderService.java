package com.epam.esm.service;

import com.epam.esm.dto.OrderRequestDto;
import com.epam.esm.entity.OrderEntity;

import java.util.List;

public interface OrderService extends BaseService<OrderEntity, OrderRequestDto> {
    List<OrderEntity> getAll();
}
