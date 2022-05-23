package com.epam.esm.service.impl;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ApplicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final ApplicationValidator validator;

    @Autowired
    public TagServiceImpl(TagRepository repository, ApplicationValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public TagEntity create(TagRequestDto tagRequestDto) {
        if (validator.isNameValid(tagRequestDto.getName())) {
            throw new ApplicationNotValidDataException("tag name is not valid", tagRequestDto.getName());
        }
        return null;
    }

    @Override
    public TagEntity get(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException("tag id is not valid", id);
        }
        return Optional.ofNullable(repository.findById(id)).orElseThrow(() ->
                new ApplicationNotFoundException("tag not found with given id", id));
    }

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
