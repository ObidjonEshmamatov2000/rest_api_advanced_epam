package com.epam.esm.controller;

import com.epam.esm.dto.OrderRequestDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), "success", service.getAll());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") long id) {
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), "success", service.get(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OrderRequestDto orderRequestDto, BindingResult bindingResult) {
        BaseResponse success =
                new BaseResponse(HttpStatus.CREATED.value(), "success", service.create(orderRequestDto, bindingResult));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), "success");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }
}
