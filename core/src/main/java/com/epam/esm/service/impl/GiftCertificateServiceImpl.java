package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ApplicationDuplicateDataException;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ApplicationValidator;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final ApplicationValidator validator;
    private final ModelMapper modelMapper;
    private final TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository, ApplicationValidator validator, ModelMapper modelMapper, TagService tagService) {
        this.repository = repository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.tagService = tagService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GiftCertificateEntity create(GiftCertificateRequestDto request, BindingResult bindingResult) {
        checkIfModelValidForCreating(request, bindingResult);
        GiftCertificateEntity entity = modelMapper.map(request, GiftCertificateEntity.class);
        entity.setCreateDate(getCurrentTime());
        entity.setLastUpdateDate(getCurrentTime());
        entity.setTags(checkIfTagExist(entity.getTags()));
        return repository.create(entity);
    }

    private void checkIfModelValidForCreating(GiftCertificateRequestDto request, BindingResult bindingResult) {
        checkIfModelValid(request, bindingResult);
        if (!validator.isNameValid(request.getName()) || !validator.isDescriptionValid(request.getDescription())) {
            throw new ApplicationNotValidDataException("name or description is not valid", request.getName());
        }
        List<GiftCertificateEntity> byName = repository.findByName(request.getName());
        if (!byName.isEmpty()) {
            throw new ApplicationDuplicateDataException("gift certificate with this name is already exist", request);
        }
    }

    private Set<TagEntity> checkIfTagExist(Set<TagEntity> tags) {
        Set<TagEntity> tagEntities = new HashSet<>();
        tags.forEach(tag -> {
            List<TagEntity> tagsByName = tagService.getTagsByName(tag.getName());
            if (tagsByName.isEmpty()) {
                tagEntities.add(tag);
            } else {
                tagEntities.add(tagsByName.get(0));
            }
        });
        return tagEntities;
    }

    @Override
    public GiftCertificateEntity get(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException("the gift certificate id is not valid", id);
        }
        return Optional.ofNullable(repository.findById(id))
                .orElseThrow(() -> new ApplicationNotFoundException("gift certificate not found", id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException("the gift certificate id is not valid", id);
        }
        repository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GiftCertificateEntity update(GiftCertificateRequestDto update, Long certificateId, BindingResult bindingResult) {
        GiftCertificateEntity old = checkIfModelValidForUpdatingAndGetOld(update, certificateId, bindingResult);
        Set<TagEntity> tagEntities = old.getTags(); // star, moon
        System.out.println("before all");
        tagEntities.forEach(tagEntity -> System.out.println(tagEntity.getName()));
        Set<TagRequestDto> updateTags = update.getTags();
        update.setTags(null);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(update, old);
        if (updateTags != null && updateTags.size() != 0) {
            updateTags.forEach(tag -> {
                List<TagEntity> tagsByName = tagService.getTagsByName(tag.getName()); // mafia
                if (!tagsByName.isEmpty()) {
                    tagEntities.add(tagsByName.get(0));
                } else {
                    tagEntities.add(new TagEntity(null, tag.getName(), getCurrentTime()));
                }
            });
        }
        System.out.println("after update");
        tagEntities.forEach(tagEntity -> System.out.println(tagEntity.getName()));
        old.setTags(tagEntities);
        old.setLastUpdateDate(getCurrentTime());
        System.out.println("after all");
        tagEntities.forEach(tagEntity -> System.out.println(tagEntity.getName()));
        return repository.update(old);
    }

    private GiftCertificateEntity checkIfModelValidForUpdatingAndGetOld(GiftCertificateRequestDto update, Long certificateId, BindingResult bindingResult) {
        if (!validator.isNumberValid(certificateId)) {
            throw new ApplicationNotValidDataException("gift certificate id is not valid", certificateId);
        }
        checkIfModelValid(update, bindingResult);
        GiftCertificateEntity old = repository.findById(certificateId);
        if (old == null) {
            throw new ApplicationNotFoundException("gift certificate not found with given id", update);
        }
        return old;
    }

    private void checkIfModelValid(GiftCertificateRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ApplicationNotValidDataException(errors.get(0).getDefaultMessage(), bindingResult.getTarget());
        }
        if (request.getTags() != null) {
            request.getTags().forEach(tag -> {
                if (!validator.isNameValid(tag.getName())) {
                    throw new ApplicationNotValidDataException("tag name is not valid", request.getTags());
                }
            });
        }
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }
}
