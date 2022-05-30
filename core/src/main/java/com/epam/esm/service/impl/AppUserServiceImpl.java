package com.epam.esm.service.impl;

import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.exception.ApplicationNotFoundException;
import com.epam.esm.exception.ApplicationNotValidDataException;
import com.epam.esm.repository.AppUserRepository;
import com.epam.esm.service.AppUserService;
import com.epam.esm.utils.ApplicationValidator;
import com.epam.esm.utils.PaginationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.utils.ParamsStringProvider.*;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository repository;
    private final ApplicationValidator validator;
    private final PaginationProvider paginationProvider;

    @Autowired
    public AppUserServiceImpl(AppUserRepository repository, ApplicationValidator validator, PaginationProvider paginationProvider) {
        this.repository = repository;
        this.validator = validator;
        this.paginationProvider = paginationProvider;
    }

    @Override
    public List<AppUserEntity> getAllUsers(Map<String, Object> params) {
        List<AppUserEntity> all;
        String name = (String) params.get(NAME);
        String email = (String) params.get(EMAIL);

        if (validator.isNameValid(name)) {
            all = repository.getAllUsersByName(
                    name,
                    paginationProvider.getPaginationParam(params)
            );
        } else if (validator.isNameValid(email)) {
            all = repository.getAllUsersByEmail(
                    email,
                    paginationProvider.getPaginationParam(params)
            );
        } else {
            all = repository.getAllUsers(paginationProvider.getPaginationParam(params));
        }
        return all;
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
