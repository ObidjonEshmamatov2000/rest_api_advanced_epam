package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderRequestDto;
import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.AppUserService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.utils.ApplicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final ApplicationValidator validator;
    private final AppUserService appUserService;
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, ApplicationValidator validator, AppUserService appUserService, GiftCertificateService giftCertificateService) {
        this.repository = repository;
        this.validator = validator;
        this.appUserService = appUserService;
        this.giftCertificateService = giftCertificateService;
    }


    @Override
    public OrderEntity create(OrderRequestDto orderRequestDto, BindingResult bindingResult) {
        OrderEntity order;
        AppUserEntity appUserEntity = appUserService.getUserById(orderRequestDto.getUserId());

        List<GiftCertificateEntity> giftCertificateEntities = new ArrayList<>();
        orderRequestDto.getCertificateIds().forEach(id ->
                giftCertificateEntities.add(giftCertificateService.get(id)));

        if (giftCertificateEntities.isEmpty()) {
            throw new ApplicationNotValidDataException("no gift certificate is selected", orderRequestDto);
        } else {
            order = new OrderEntity(
                    findTotalOrderCost(giftCertificateEntities),
                    getCurrentTime(),
                    giftCertificateEntities,
                    appUserEntity);
        }
        return repository.create(order);
    }

    private BigDecimal findTotalOrderCost(List<GiftCertificateEntity> giftCertificateEntities) {
        BigDecimal total = BigDecimal.ZERO;
        for (GiftCertificateEntity entity: giftCertificateEntities) {
            total = total.add(entity.getPrice());
        }
        return total;
    }

    @Override
    public OrderEntity get(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException("order id is not valid", id);
        }
        return Optional.ofNullable(repository.findById(id)).orElseThrow(() ->
                new ApplicationNotFoundException("order not found with given id", id));
    }

    @Override
    public void delete(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException("order id is not valid", id);
        }
        repository.delete(id);
    }

    @Override
    public List<OrderEntity> getAll() {
        return null;
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }
}
