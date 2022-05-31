package com.epam.esm.repository.impl;

import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.repository.AppUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import static com.epam.esm.utils.ParamsStringProvider.*;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */


@Repository
public class AppUserRepositoryImpl implements AppUserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AppUserEntity merge(AppUserEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public List<AppUserEntity> findAll(Map<String, Integer> paginationParam) {
        String query = "select u from AppUserEntity u";
        return entityManager
                .createQuery(query,AppUserEntity.class)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public int deleteById(Long aLong) {
        return entityManager
                .createQuery("delete from AppUserEntity u where u.id = :id")
                .setParameter("id", aLong)
                .executeUpdate();
    }

    @Override
    public AppUserEntity findById(Long aLong) {
        return entityManager.find(AppUserEntity.class, aLong);
    }

    @Override
    public List<AppUserEntity> findAllUsersByName(String name, Map<String, Integer> paginationParam) {
        String query = "select u from AppUserEntity u " +
                "where u.firstName like CONCAT('%', ?1, '%') " +
                "or u.lastName like CONCAT('%', ?1, '%')";
        return entityManager
                .createQuery(query,AppUserEntity.class)
                .setParameter(1, name)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public List<AppUserEntity> findAllUsersByEmail(String email, Map<String, Integer> paginationParam) {
        String query = "select u from AppUserEntity u where u.email like CONCAT('%', ?1, '%')";
        return entityManager
                .createQuery(query,AppUserEntity.class)
                .setParameter(1, email)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }
}
