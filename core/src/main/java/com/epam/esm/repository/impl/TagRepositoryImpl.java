package com.epam.esm.repository.impl;

import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
@Repository
public class TagRepositoryImpl implements TagRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TagEntity merge(TagEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public List<TagEntity> findAll(PaginationParams paginationParams) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> cq = cb.createQuery(TagEntity.class);
        Root<TagEntity> entityRoot = cq.from(TagEntity.class);
        cq.select(entityRoot);
        return entityManager.createQuery(cq)
                .setFirstResult(paginationParams.getPageNumber())
                .setMaxResults(paginationParams.getPageSize())
                .getResultList();
    }

    @Override
    public int deleteById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<TagEntity> criteriaDelete = cb.createCriteriaDelete(TagEntity.class);
        Root<TagEntity> tagEntityRoot = criteriaDelete.from(TagEntity.class);
        criteriaDelete.where(cb.equal(tagEntityRoot.get("id"), id));
        return entityManager
                .createQuery(criteriaDelete)
                .executeUpdate();
    }

    @Override
    public TagEntity findById(Long id) {
        return entityManager.find(TagEntity.class, id);
    }

    @Override
    public List<TagEntity> findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> cq = cb.createQuery(TagEntity.class);
        Root<TagEntity> entityRoot = cq.from(TagEntity.class);
        cq.select(entityRoot).where(cb.equal(entityRoot.get("name"), name));
        return entityManager
                .createQuery(cq)
                .getResultList();
    }

    @Override
    public List<TagEntity> findTagsByCertificateId(Integer giftCertificateId, PaginationParams paginationParams) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> cq = cb.createQuery(TagEntity.class);
        Root<TagEntity> entityRoot = cq.from(TagEntity.class);
        Join<GiftCertificateEntity, TagEntity> join = entityRoot.join("certificates");
        cq.where(cb.equal(join.get("id"), giftCertificateId));
        return entityManager
                .createQuery(cq)
                .setFirstResult(paginationParams.getPageNumber())
                .setMaxResults(paginationParams.getPageSize())
                .getResultList();
    }

    @Override
    public List<TagEntity> findMostUsedTagOfUserWithHighestCostOfOrders() {
        return (List<TagEntity>) entityManager
                .createStoredProcedureQuery("get_most_widely_used_tag_of_user_with_highest_cost", TagEntity.class)
                .getResultList();
    }
}
