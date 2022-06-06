package com.epam.esm.controller;

import com.epam.esm.assembler.OrderModelAssembler;
import com.epam.esm.common.BaseResponse;
import com.epam.esm.dto.OrderRequestDto;
import com.epam.esm.dto.OrderResponseDto;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.*;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;
    private final OrderModelAssembler assembler;

    @Autowired
    public OrderController(OrderService service, OrderModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAllOrders(
            @RequestParam(name = "userId", required = false) Integer userID,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put(PAGE_SIZE, pageSize);
        params.put(PAGE_NUMBER, pageNumber);
        params.put(USER_ID, userID);

        List<OrderEntity> entities = service.findAll(params);
        BaseResponse success = new BaseResponse(
                HttpStatus.OK.value(),
                SUCCESS_MESSAGE,
                assembler.toCollectionModels(entities)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/userOrder")
    public ResponseEntity<BaseResponse> getUserOrders(
            @RequestParam(name = "userId", required = false) Integer userID,
            @RequestParam(value = "orderId", required = false) Integer orderId
    ) {
        OrderResponseDto userOrder = service.findSingleUserOrder(userID, orderId);
        BaseResponse success = new BaseResponse(
                HttpStatus.OK.value(),
                SUCCESS_MESSAGE,
                userOrder
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getOrderById(@PathVariable("id") long id) {
        OrderEntity entity = service.findById(id);
        BaseResponse success = new BaseResponse(
                HttpStatus.OK.value(),
                SUCCESS_MESSAGE,
                assembler.toModel(entity)
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(
            @Valid @RequestBody OrderRequestDto orderRequestDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ApplicationNotValidDataException(
                    errors.get(0).getDefaultMessage(),
                    bindingResult.getTarget()
            );
        }

        OrderEntity order = service.create(orderRequestDto);
        BaseResponse success = new BaseResponse(
                HttpStatus.CREATED.value(),
                SUCCESS_MESSAGE,
                assembler.toModel(order)
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(success);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        BaseResponse success = new BaseResponse(HttpStatus.OK.value(), SUCCESS_MESSAGE);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(success);
    }
}
