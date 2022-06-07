package com.epam.esm.service;

import com.epam.esm.dto.params.AppUserParams;
import com.epam.esm.entity.AppUserEntity;

import java.util.List;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface AppUserService {
    List<AppUserEntity> findAllUsers(AppUserParams appUserParams);

    AppUserEntity findUserById(Long id);
}
