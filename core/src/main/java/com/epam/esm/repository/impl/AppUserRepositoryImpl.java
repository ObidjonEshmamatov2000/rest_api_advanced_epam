package com.epam.esm.repository.impl;

import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.repository.AppUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AppUserRepositoryImpl implements AppUserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AppUserEntity create(AppUserEntity obj) {
        return null;
    }

    @Override
    public List<AppUserEntity> getAll(int limit, int offset) {
        return null;
    }

    @Override
    public AppUserEntity findById(Long aLong) {
        return entityManager.find(AppUserEntity.class, aLong);
    }

    @Override
    public AppUserEntity update(AppUserEntity obj) {
        return null;
    }

    @Override
    public int delete(Long aLong) {
        return 0;
    }
}
