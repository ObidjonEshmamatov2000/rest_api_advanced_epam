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

import static com.epam.esm.utils.ParamsStringProvider.ID_NOT_VALID;
import static com.epam.esm.utils.ParamsStringProvider.USER_NOT_FOUND;

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
            throw new ApplicationNotValidDataException(ID_NOT_VALID, id);
        }
        return Optional.ofNullable(repository.findById(id)).orElseThrow(() ->
                new ApplicationNotFoundException(USER_NOT_FOUND, id));
    }
}
