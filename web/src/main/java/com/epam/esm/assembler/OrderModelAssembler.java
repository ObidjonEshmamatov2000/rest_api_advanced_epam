package com.epam.esm.assembler;

import com.epam.esm.controller.AppUserController;
import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.model.AppUserModel;
import com.epam.esm.dto.model.GiftCertificateModel;
import com.epam.esm.dto.model.OrderModel;
import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderEntity;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 06/06/2022 - 9:54 PM
 */
public class OrderModelAssembler
        extends RepresentationModelAssemblerSupport<OrderEntity, OrderModel> {

    public OrderModelAssembler(Class<?> controllerClass, Class<OrderModel> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public OrderModel toModel(OrderEntity entity) {
        OrderModel model = instantiateModel(entity);

        model.add(linkTo(
                methodOn(OrderController.class).getOrderById(entity.getId()))
                .withSelfRel()
        );

        model.setId(entity.getId());
        model.setCost(entity.getCost());
        model.setCreateDate(entity.getCreateDate());
        model.setUser(toAppUserModel(entity.getUser()));
        model.setGiftCertificates(toGiftCertificateModel(entity.getCertificates()));
        return model;
    }

    public List<OrderModel> toCollectionModels(List<OrderEntity> entities) {
        if (entities.isEmpty())
            return Collections.emptyList();
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private List<GiftCertificateModel> toGiftCertificateModel(List<GiftCertificateEntity> certificates) {
        if (certificates.isEmpty())
            return Collections.emptyList();
        return certificates.stream()
                .map(certificate -> GiftCertificateModel.builder()
                        .id(certificate.getId())
                        .name(certificate.getName())
                        .description(certificate.getDescription())
                        .price(certificate.getPrice())
                        .duration(certificate.getDuration())
                        .createDate(certificate.getCreateDate())
                        .lastUpdateDate(certificate.getLastUpdateDate())
                        .build()
                        .add(linkTo(
                                methodOn(GiftCertificateController.class).getById(certificate.getId()))
                                .withSelfRel()
                        )
                )
                .collect(Collectors.toList());
    }

    private AppUserModel toAppUserModel(AppUserEntity user) {
        if (user == null) return null;

        return AppUserModel.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build()
                .add(linkTo(
                        methodOn(AppUserController.class).getUserById(user.getId()))
                        .withSelfRel()
                );
    }
}
