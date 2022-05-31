package com.epam.esm.service;

import com.epam.esm.entity.AppUserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

public interface AppUserService {
    List<AppUserEntity> findAllUsers(Map<String, Object> params);

    AppUserEntity findUserById(Long id);
}
