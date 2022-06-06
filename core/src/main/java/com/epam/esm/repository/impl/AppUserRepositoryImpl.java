package com.epam.esm.repository.impl;

import com.epam.esm.entity.AppUserEntity;
import com.epam.esm.repository.AppUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

import static com.epam.esm.utils.ParamsStringProvider.LIMIT;
import static com.epam.esm.utils.ParamsStringProvider.OFFSET;

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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppUserEntity> cq = cb.createQuery(AppUserEntity.class);
        Root<AppUserEntity> appUserEntityRoot = cq.from(AppUserEntity.class);
        cq.select(appUserEntityRoot);

        return entityManager.createQuery(cq)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public int deleteById(Long aLong) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<AppUserEntity> criteriaDelete = cb.createCriteriaDelete(AppUserEntity.class);
        Root<AppUserEntity> appUserEntityRoot = criteriaDelete.from(AppUserEntity.class);
        criteriaDelete.where(cb.equal(appUserEntityRoot.get("id"), aLong));
        return entityManager
                .createQuery(criteriaDelete)
                .executeUpdate();
    }

    @Override
    public AppUserEntity findById(Long aLong) {
        return entityManager.find(AppUserEntity.class, aLong);
    }

    @Override
    public List<AppUserEntity> findAllUsersByName(String name, Map<String, Integer> paginationParam) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppUserEntity> cq = cb.createQuery(AppUserEntity.class);
        Root<AppUserEntity> entityRoot = cq.from(AppUserEntity.class);
        cq.select(entityRoot).where(cb.or(
                                    cb.like(entityRoot.get("firstName"), "%" + name + "%"),
                                    cb.like(entityRoot.get("lastName"), "%" + name + "%")
                                ));
        return entityManager
                .createQuery(cq)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public List<AppUserEntity> findAllUsersByEmail(String email, Map<String, Integer> paginationParam) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AppUserEntity> cq = cb.createQuery(AppUserEntity.class);
        Root<AppUserEntity> entityRoot = cq.from(AppUserEntity.class);
        cq.select(entityRoot).where(cb.like(entityRoot.get("email"), "%" + email + "%"));
        return entityManager
                .createQuery(cq)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }
}
