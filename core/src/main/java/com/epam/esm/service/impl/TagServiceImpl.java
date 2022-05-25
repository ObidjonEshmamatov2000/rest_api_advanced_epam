package com.epam.esm.service.impl;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ApplicationDuplicateDataException;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ApplicationValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final ApplicationValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TagServiceImpl(TagRepository repository, ApplicationValidator validator, ModelMapper modelMapper) {
        this.repository = repository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TagEntity create(TagRequestDto tagRequestDto, BindingResult bindingResult) {
        checkIfModelValid(tagRequestDto, bindingResult);

        TagEntity map = modelMapper.map(tagRequestDto, TagEntity.class);
        return repository.create(map);
    }

    public void checkIfModelValid(TagRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ApplicationNotValidDataException(errors.get(0).getDefaultMessage(), bindingResult.getTarget());
        }
        if (!validator.isNameValid(request.getName())) {
            throw new ApplicationNotValidDataException("tag name is not valid", request.getName());
        }
        List<TagEntity> byName = repository.findByName(request.getName());
        if (!byName.isEmpty()) {
            throw new ApplicationDuplicateDataException("tag with this name is already exist", request.getName());
        }
    }

    @Override
    public TagEntity get(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException("tag id is not valid", id);
        }
        return Optional.ofNullable(repository.findById(id)).orElseThrow(() ->
                new ApplicationNotFoundException("tag not found with given id", id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException("tag id is not valid", id);
        }
        repository.delete(id);
    }

    @Override
    public List<TagEntity> getAll() {
        return repository.getAll(1, 1);
    }
}
