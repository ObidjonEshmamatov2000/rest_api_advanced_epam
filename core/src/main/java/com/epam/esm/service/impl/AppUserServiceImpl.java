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

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository repository;
    private final ApplicationValidator validator;
    private final PaginationProvider paginationProvider;

    @Autowired
    public AppUserServiceImpl(
            AppUserRepository repository,
            ApplicationValidator validator,
            PaginationProvider paginationProvider
    ) {
        this.repository = repository;
        this.validator = validator;
        this.paginationProvider = paginationProvider;
    }

    @Override
    public List<AppUserEntity> findAllUsers(Map<String, Object> params) {
        List<AppUserEntity> all;
        String name = (String) params.get(NAME);
        String email = (String) params.get(EMAIL);

        if (validator.isNameValid(name)) {
            all = repository.findAllUsersByName(
                    name,
                    paginationProvider.getPaginationParam(params)
            );
        } else if (validator.isEmailValid(email)) {
            all = repository.findAllUsersByEmail(
                    email,
                    paginationProvider.getPaginationParam(params)
            );
        } else {
            all = repository.findAll(paginationProvider.getPaginationParam(params));
        }
        return all;
    }

    @Override
    public AppUserEntity findUserById(Long id) {
        if (!validator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException(ID_NOT_VALID, id);
        }
        return Optional.ofNullable(repository.findById(id)).orElseThrow(() ->
                new ApplicationNotFoundException(USER_NOT_FOUND, id));
    }
}
