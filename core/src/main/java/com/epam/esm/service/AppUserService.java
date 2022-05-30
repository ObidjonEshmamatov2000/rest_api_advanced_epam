package com.epam.esm.service;

import com.epam.esm.entity.AppUserEntity;

import java.util.List;
import java.util.Map;

public interface AppUserService {
    List<AppUserEntity> getAllUsers(Map<String, Object> params);
    AppUserEntity getUserById(Long id);
}
