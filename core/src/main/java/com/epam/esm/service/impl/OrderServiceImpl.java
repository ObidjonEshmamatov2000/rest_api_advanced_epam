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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.utils.ParamsStringProvider.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final ApplicationValidator validator;
    private final AppUserService appUserService;
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public OrderServiceImpl(
            OrderRepository repository,
            ApplicationValidator validator,
            AppUserService appUserService,
            GiftCertificateService giftCertificateService
    ) {
        this.repository = repository;
        this.validator = validator;
        this.appUserService = appUserService;
        this.giftCertificateService = giftCertificateService;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderEntity create(OrderRequestDto orderRequestDto, BindingResult bindingResult) {
        OrderEntity order;
        AppUserEntity appUserEntity = appUserService.getUserById(orderRequestDto.getUserId());
        List<GiftCertificateEntity> giftCertificateEntities = new ArrayList<>();
        orderRequestDto.getCertificateIds().forEach(id ->
                giftCertificateEntities.add(giftCertificateService.get(id)));
        if (giftCertificateEntities.isEmpty()) {
            throw new ApplicationNotValidDataException(NO_CERTIFICATE_SELECTED_TO_ORDER, orderRequestDto);
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
            throw new ApplicationNotValidDataException(ID_NOT_VALID, id);
        }
        return Optional.ofNullable(repository.findById(id)).orElseThrow(() ->
                new ApplicationNotFoundException(ORDER_NOT_FOUND, id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException(ID_NOT_VALID, id);
        }
        repository.delete(id);
    }

    @Override
    public List<OrderEntity> getAll(Map<String, Object> params) {
        return null;
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }
}
