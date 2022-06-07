package com.epam.esm.service.impl;

import com.epam.esm.dto.params.AppUserParams;
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
    public List<AppUserEntity> findAllUsers(AppUserParams appUserParams) {
        List<AppUserEntity> all;
        String name = appUserParams.getName();
        String email = appUserParams.getEmail();

        if (name != null) {
            if (!validator.isNameValid(name)) {
                throw new ApplicationNotValidDataException(NAME_NOT_VALID, name);
            }
            all = repository.findAllUsersByName(
                    name,
                    paginationProvider.getPaginationParams(appUserParams.getPaginationParams())
            );
        } else if (email != null) {
            if (!validator.isEmailValid(email)) {
                throw new ApplicationNotValidDataException(EMAIL_NOT_VALID, email);
            }
            all = repository.findAllUsersByEmail(
                    email,
                    paginationProvider.getPaginationParams(appUserParams.getPaginationParams())
            );
        } else {
            all = repository.findAll(
                    paginationProvider.getPaginationParams(appUserParams.getPaginationParams())
            );
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
