package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.exception.ApplicationDuplicateDataException;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
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
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final ApplicationValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository, ApplicationValidator validator, ModelMapper modelMapper) {
        this.repository = repository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GiftCertificateEntity create(GiftCertificateRequestDto request, BindingResult bindingResult) {
        checkIfModelValid(request, bindingResult);
        List<GiftCertificateEntity> byName = repository.findByName(request.getName());
        if (!byName.isEmpty()) {
            throw new ApplicationDuplicateDataException("gift certificate with this name is already exist", request);
        }
        GiftCertificateEntity entity = modelMapper.map(request, GiftCertificateEntity.class);
        entity.setCreateDate(getCurrentTime());
        entity.setLastUpdateDate(getCurrentTime());
        return repository.create(entity);
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
        checkIfModelValid(update, bindingResult);

        GiftCertificateEntity old = repository.findById(certificateId);
        if (old == null) {
            throw new ApplicationNotFoundException("gift certificate not found with given id", update);
        }

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(update, old);
        old.setLastUpdateDate(getCurrentTime());

        return repository.update(old);
    }

    private void checkIfModelValid(GiftCertificateRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ApplicationNotValidDataException(errors.get(0).getDefaultMessage(), bindingResult.getTarget());
        }
        if (!validator.isNameValid(request.getName()) || !validator.isDescriptionValid(request.getDescription())) {
            throw new ApplicationNotValidDataException("name or description is not valid", request.getName());
        }

        if (request.getTags() != null && request.getTags().size() > 0) {
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
