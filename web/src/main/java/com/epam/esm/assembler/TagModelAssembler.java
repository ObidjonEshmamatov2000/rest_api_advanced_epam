package com.epam.esm.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.model.TagModel;
import com.epam.esm.entity.TagEntity;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 06/06/2022 - 9:22 PM
 */
public class TagModelAssembler extends RepresentationModelAssemblerSupport<TagEntity, TagModel> {

    public TagModelAssembler(Class<?> controllerClass, Class<TagModel> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public TagModel toModel(TagEntity entity) {
        TagModel tagModel = instantiateModel(entity);
        tagModel.add(linkTo(
                methodOn(TagController.class).get(entity.getId()))
                .withSelfRel()
        );

        tagModel.setId(entity.getId());
        tagModel.setName(entity.getName());
        tagModel.setCreateDate(entity.getCreateDate());
        return tagModel;
    }

    public List<TagModel> toCollectionModels(List<TagEntity> entities) {
        if (entities.isEmpty())
            return Collections.emptyList();
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
