package com.epam.esm.hateos.assembler;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.hateos.model.GiftCertificateModel;
import com.epam.esm.hateos.model.TagModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

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
@Component
public class GiftCertificateModelAssembler
        extends RepresentationModelAssemblerSupport<GiftCertificateEntity, GiftCertificateModel> {

    public GiftCertificateModelAssembler() {
        super(GiftCertificateController.class, GiftCertificateModel.class);
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
