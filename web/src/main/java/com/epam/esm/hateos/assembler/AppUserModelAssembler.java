package com.epam.esm.hateos.assembler;

import com.epam.esm.controller.AppUserController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.hateos.model.AppUserModel;
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
public class AppUserModelAssembler
        extends RepresentationModelAssemblerSupport<AppUserEntity, AppUserModel> {

    public AppUserModelAssembler() {
        super(AppUserController.class, AppUserModel.class);
    }

    @Override
    public AppUserModel toModel(AppUserEntity entity) {
        AppUserModel model = instantiateModel(entity);

        model.add(linkTo(
                methodOn(AppUserController.class).getUserById(entity.getId()))
                .withSelfRel());

        model.setId(entity.getId());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setEmail(entity.getEmail());
        model.setOrders(toOrderModel(entity.getOrders()));
        return model;
    }
    
    public List<AppUserModel> toCollectionModels(List<AppUserEntity> entities) {
        if (entities.isEmpty())
            return Collections.emptyList();
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private List<OrderModel> toOrderModel(List<OrderEntity> orders) {
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        return orders.stream()
                .map(order -> OrderModel.builder()
                        .id(order.getId())
                        .cost(order.getCost())
                        .createDate(order.getCreateDate())
                        .build()
                        .add(linkTo(
                                methodOn(OrderController.class).getOrderById(order.getId()))
                                .withSelfRel()
                        )
                )
                .collect(Collectors.toList());
    }
}
