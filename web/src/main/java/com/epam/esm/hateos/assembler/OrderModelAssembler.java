package com.epam.esm.hateos.assembler;

import com.epam.esm.controller.AppUserController;
import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.hateos.model.AppUserModel;
import com.epam.esm.hateos.model.GiftCertificateModel;
import com.epam.esm.hateos.model.OrderModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

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
@Component
public class OrderModelAssembler
        extends RepresentationModelAssemblerSupport<OrderEntity, OrderModel> {

    public OrderModelAssembler() {
        super(OrderController.class, OrderModel.class);
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
