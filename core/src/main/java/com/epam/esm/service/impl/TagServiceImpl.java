package com.epam.esm.service.impl;

import com.epam.esm.dto.params.TagParams;
import com.epam.esm.dto.request.TagRequestDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ApplicationDuplicateDataException;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.PaginationProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<TagEntity> findTagsByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<TagEntity> findMostUsedTagOfUserWithHighestCostOfOrders() {
        return repository.findMostUsedTagOfUserWithHighestCostOfOrders();
    }

    @Override
    public List<TagEntity> findAllTags(TagParams tagParams) {
        List<TagEntity> all;
        if (tagParams.getName() != null) {
            if (!validator.isNameValid(tagParams.getName())) {
                throw new ApplicationNotValidDataException(NAME_NOT_VALID, tagParams.getName());
            }
            all = repository.findByName(tagParams.getName());
        } else if (tagParams.getCertificateId() != null) {
            if (!validator.isNumberValid(tagParams.getCertificateId())) {
                throw new ApplicationNotValidDataException(ID_NOT_VALID, tagParams.getCertificateId());
            }
            all = repository.findTagsByCertificateId(
                    tagParams.getCertificateId(),
                    paginationProvider.getPaginationParams(tagParams.getPaginationParams())
            );
        } else {
            all = repository.findAll(
                    paginationProvider.getPaginationParams(tagParams.getPaginationParams())
            );
        }
        return all;
    }
}
