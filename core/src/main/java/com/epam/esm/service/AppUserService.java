package com.epam.esm.service;

import com.epam.esm.entity.AppUserEntity;

import java.util.List;

public interface AppUserService {
    List<AppUserEntity> getAllUsers();
    AppUserEntity getUserById(Long id);
}
