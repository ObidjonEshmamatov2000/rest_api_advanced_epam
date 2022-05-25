package com.epam.esm.service.impl;

import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.AppUserRepository;
import com.epam.esm.service.AppUserService;
import com.epam.esm.utils.ApplicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository repository;
    private final ApplicationValidator validator;

    @Autowired
    public AppUserServiceImpl(AppUserRepository repository, ApplicationValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public List<AppUserEntity> getAllUsers() {
        return null;
    }

    @Override
    public AppUserEntity getUserById(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException("user id is not valid", id);
        }
        return Optional.ofNullable(repository.findById(id)).orElseThrow(() ->
                new ApplicationNotFoundException("user not found with given id", id));
    }
}
