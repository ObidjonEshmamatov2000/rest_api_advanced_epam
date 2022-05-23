package com.epam.esm.service.impl;

import com.epam.esm.repository.AppUserRepository;
import com.epam.esm.service.AppUserService;
import com.epam.esm.utils.ApplicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository repository;
    private final ApplicationValidator validator;

    @Autowired
    public AppUserServiceImpl(AppUserRepository repository, ApplicationValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }
}
