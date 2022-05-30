package com.epam.esm.repository.impl;

import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.repository.AppUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.*;

@Repository
public class AppUserRepositoryImpl implements AppUserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AppUserEntity create(AppUserEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public List<AppUserEntity> getAll(int limit, int offset) {
        return entityManager.createQuery("select u from AppUserEntity u", AppUserEntity.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public AppUserEntity findById(Long aLong) {
        return entityManager.find(AppUserEntity.class, aLong);
    }

    @Override
    public AppUserEntity update(AppUserEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public int delete(Long aLong) {
        return entityManager.createQuery("delete from AppUserEntity u where u.id = :id")
                .setParameter("id", aLong)
                .executeUpdate();
    }

    @Override
    public List<AppUserEntity> getAllUsersByName(String name, Map<String, Integer> paginationParam) {
        String query = "select u from AppUserEntity u where u.firstName like CONCAT('%', ?1, '%') or u.lastName like CONCAT('%', ?1, '%')";
        return entityManager.createQuery(
                query,
                AppUserEntity.class
        )
                .setParameter(1, name)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public List<AppUserEntity> getAllUsersByEmail(String email, Map<String, Integer> paginationParam) {
        String query = "select u from AppUserEntity u where u.email like CONCAT('%', ?1, '%')";
        return entityManager.createQuery(
                        query,
                        AppUserEntity.class
                )
                .setParameter(1, email)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public List<AppUserEntity> getAllUsers(Map<String, Integer> paginationParam) {
        String query = "select u from AppUserEntity u";
        return entityManager.createQuery(
                        query,
                        AppUserEntity.class
                )
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }
}
