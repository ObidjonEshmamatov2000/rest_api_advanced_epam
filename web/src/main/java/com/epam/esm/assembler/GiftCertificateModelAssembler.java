package com.epam.esm.assembler;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.model.GiftCertificateModel;
import com.epam.esm.dto.model.TagModel;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 06/06/2022 - 9:42 PM
 */
public class GiftCertificateModelAssembler
        extends RepresentationModelAssemblerSupport<GiftCertificateEntity, GiftCertificateModel> {

    public GiftCertificateModelAssembler(Class<?> controllerClass, Class<GiftCertificateModel> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public GiftCertificateModel toModel(GiftCertificateEntity entity) {
        GiftCertificateModel model = instantiateModel(entity);
        model.add(linkTo(
                methodOn(GiftCertificateController.class).getById(entity.getId()))
                .withSelfRel()
        );

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setPrice(entity.getPrice());
        model.setDuration(entity.getDuration());
        model.setCreateDate(entity.getCreateDate());
        model.setLastUpdateDate(entity.getLastUpdateDate());
        model.setTags(toTagModel(entity.getTags()));
        return model;
    }

    public List<GiftCertificateModel> toCollectionModels(List<GiftCertificateEntity> entities) {
        if (entities.isEmpty())
            return Collections.emptyList();
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private List<TagModel> toTagModel(Set<TagEntity> tags) {
        if (tags.isEmpty())
            return Collections.emptyList();

        return tags.stream()
                .map(tag -> TagModel.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .createDate(tag.getCreateDate())
                        .build()
                        .add(linkTo(
                                methodOn(TagController.class).get(tag.getId()))
                                .withSelfRel()
                        )
                )
                .collect(Collectors.toList());
    }
}
