package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.utils.ParamsStringProvider;
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
public class TagRepositoryImpl implements TagRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TagEntity merge(TagEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public List<TagEntity> findAll(Map<String, Integer> paginationParam) {
        String query = "select t from TagEntity t";
        return entityManager
                .createQuery(query,TagEntity.class)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public int deleteById(Long id) {
        return entityManager
                .createQuery("delete from TagEntity where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public TagEntity findById(Long id) {
        return entityManager.find(TagEntity.class, id);
    }

    @Override
    public List<TagEntity> findByName(String name) {
        return entityManager
                .createQuery("select t from TagEntity t where t.name = :name", TagEntity.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<TagEntity> findTagsByCertificateId(Integer giftCertificateId, Map<String, Integer> paginationParam) {
        String query = "select t from TagEntity t join fetch t.certificates gc where gc.id = :certificateId";
        return entityManager
                .createQuery(query,TagEntity.class)
                .setParameter("certificateId", Long.valueOf(giftCertificateId))
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public List<TagEntity> findMostUsedTagOfUserWithHighestCostOfOrders() {
        String query = "select t from TagEntity t";
        return entityManager
                .createQuery(query, TagEntity.class)
                .getResultList();
    }
}
