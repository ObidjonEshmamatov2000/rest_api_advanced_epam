package com.epam.esm.repository;

import com.epam.esm.entity.AppUserEntity;

import java.util.List;
import java.util.Map;


public interface AppUserRepository extends CrudRepository<AppUserEntity, Long> {
    List<AppUserEntity> getAllUsersByName(String name, Map<String, Integer> paginationParam);

    List<AppUserEntity> getAllUsersByEmail(String email, Map<String, Integer> paginationParam);

    List<AppUserEntity> getAllUsers(Map<String, Integer> paginationParam);
}
