package com.epam.esm.repository;

import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.entity.AppUserEntity;
import java.util.List;
import java.util.Map;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface AppUserRepository extends CrudRepository<AppUserEntity, Long> {
    List<AppUserEntity> findAllUsersByName(String name, PaginationParams paginationParams);

    List<AppUserEntity> findAllUsersByEmail(String email, PaginationParams paginationParams);
}
