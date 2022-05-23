package com.epam.esm.service.impl;

import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.utils.ApplicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final ApplicationValidator validator;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, ApplicationValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }
}
