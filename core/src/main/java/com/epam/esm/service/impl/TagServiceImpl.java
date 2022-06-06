package com.epam.esm.service.impl;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ApplicationDuplicateDataException;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.PaginationProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.utils.ParamsStringProvider.*;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final ApplicationValidator validator;
    private final ModelMapper modelMapper;
    private final PaginationProvider paginationProvider;

    @Autowired
    public TagServiceImpl(
            TagRepository repository,
            ApplicationValidator validator,
            ModelMapper modelMapper,
            PaginationProvider paginationProvider) {
        this.repository = repository;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.paginationProvider = paginationProvider;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TagEntity create(TagRequestDto tagRequestDto) {
        checkIfModelValid(tagRequestDto);
        TagEntity map = modelMapper.map(tagRequestDto, TagEntity.class);
        return repository.merge(map);
    }

    public void checkIfModelValid(TagRequestDto request) {
        if (!validator.isNameValid(request.getName())) {
            throw new ApplicationNotValidDataException(NAME_NOT_VALID, request.getName());
        }
        List<TagEntity> byName = repository.findByName(request.getName());
        if (!byName.isEmpty()) {
            throw new ApplicationDuplicateDataException(TAG_ALREADY_EXIST, request.getName());
        }
    }

    @Override
    public TagEntity findById(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException(ID_NOT_VALID, id);
        }
        return Optional.ofNullable(repository.findById(id)).orElseThrow(() ->
                new ApplicationNotFoundException(TAG_NOT_FOUND, id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException(ID_NOT_VALID, id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<TagEntity> findAll(Map<String, Object> params) {
        List<TagEntity> all;
        String name = (String) params.get(NAME);
        Integer giftCertificateId = (Integer) params.get(CERTIFICATE_ID);
        if (validator.isNameValid(name)) {
            all = repository.findByName(name);
        } else if (giftCertificateId != null && validator.isNumberValid(giftCertificateId)) {
            all = repository.findTagsByCertificateId(
                    giftCertificateId,
                    paginationProvider.getPaginationParam(params)
            );
        } else {
            all = repository.findAll(paginationProvider.getPaginationParam(params));
        }
        return all;
    }

    @Override
    public List<TagEntity> findTagsByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<TagEntity> findMostUsedTagOfUserWithHighestCostOfOrders() {
        return repository.findMostUsedTagOfUserWithHighestCostOfOrders();
    }
}
