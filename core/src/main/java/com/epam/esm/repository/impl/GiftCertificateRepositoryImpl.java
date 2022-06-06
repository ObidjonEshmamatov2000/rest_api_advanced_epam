package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.utils.ParamsStringProvider;
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
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificateEntity merge(GiftCertificateEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public List<GiftCertificateEntity> findAll(Map<String, Integer> paginationParam) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> cq = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> entityRoot = cq.from(GiftCertificateEntity.class);
        cq.select(entityRoot);
        return entityManager.createQuery(cq)
                .setFirstResult(paginationParam.get(OFFSET))
                .setMaxResults(paginationParam.get(LIMIT))
                .getResultList();
    }

    @Override
    public int deleteById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<GiftCertificateEntity> criteriaDelete = cb.createCriteriaDelete(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> entityRoot = criteriaDelete.from(GiftCertificateEntity.class);
        criteriaDelete.where(cb.equal(entityRoot.get("id"), id));
        return entityManager
                .createQuery(criteriaDelete)
                .executeUpdate();
    }

    @Override
    public GiftCertificateEntity findById(Long id) {
        return entityManager.find(GiftCertificateEntity.class, id);
    }

    @Override
    public List<GiftCertificateEntity> findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> cq = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> entityRoot = cq.from(GiftCertificateEntity.class);
        cq.select(entityRoot).where(cb.equal(entityRoot.get("name"), name));
        return entityManager
                .createQuery(cq)
                .getResultList();
    }

    @Override
    public List<GiftCertificateEntity> findAllFilteredAndSortedByName(
            String name,
            Map<String, Integer> paginationParam,
            String sortingString
    ) {
        String query = "select gc from GiftCertificateEntity gc where gc.name like CONCAT('%', ?1, '%')";
        return entityManager
                .createQuery(buildSortingQuery(query, sortingString),GiftCertificateEntity.class)
                .setParameter(1, name)
                .setFirstResult(paginationParam.get(ParamsStringProvider.OFFSET))
                .setMaxResults(paginationParam.get(ParamsStringProvider.LIMIT))
                .getResultList();
    }

    private String buildSortingQuery(String query, String sortingString) {
        return sortingString.isEmpty() ? query : query + " order by " + sortingString;
    }

    @Override
    public List<GiftCertificateEntity> findAllFilteredAndSortedByDescription(
            String description,
            Map<String, Integer> paginationParam,
            String sortingString
    ) {
        String query = "select gc from GiftCertificateEntity gc where gc.description like CONCAT('%', ?1, '%')";
        return entityManager
                .createQuery(buildSortingQuery(query, sortingString), GiftCertificateEntity.class)
                .setParameter(1, description)
                .setFirstResult(paginationParam.get(ParamsStringProvider.OFFSET))
                .setMaxResults(paginationParam.get(ParamsStringProvider.LIMIT))
                .getResultList();
    }

    @Override
    public List<GiftCertificateEntity> findAllFilteredAndSortedByTagNames(
            List<String> tagNameList,
            Map<String, Integer> paginationParam,
            String sortingString
    ) {
        String query = "select g FROM GiftCertificateEntity g JOIN FETCH g.tags t";
        String tagNameQuery = buildTagNameQuery(tagNameList, query);
        return entityManager
                .createQuery(buildSortingQuery(tagNameQuery, sortingString), GiftCertificateEntity.class)
                .setFirstResult(paginationParam.get(ParamsStringProvider.OFFSET))
                .setMaxResults(paginationParam.get(ParamsStringProvider.LIMIT))
                .getResultList();
    }

    //String query = "select gc from GiftCertificateEntity gc inner join TagEntity t on t.id = gc.id where t.name in (' ')";
    private String buildTagNameQuery(List<String> tagNameList, String query) {
        StringBuilder tagNameQuery = new StringBuilder(query).append(" where t.name in (");
        for (int i=0; i<tagNameList.size(); i++) {
            if (i != 0) {
                tagNameQuery.append(", ").append("'").append(tagNameList.get(i)).append("'");
            } else {
                tagNameQuery.append("'").append(tagNameList.get(i)).append("'");
            }
        }
        tagNameQuery.append(")");
        return tagNameQuery.toString();
    }

    @Override
    public List<GiftCertificateEntity> findAllFilteredAndSorted(
            Map<String, Integer> paginationParam,
            String sortingString
    ) {
        String query = "select gc from GiftCertificateEntity gc";
        return entityManager
                .createQuery(buildSortingQuery(query, sortingString), GiftCertificateEntity.class)
                .setFirstResult(paginationParam.get(ParamsStringProvider.OFFSET))
                .setMaxResults(paginationParam.get(ParamsStringProvider.LIMIT))
                .getResultList();
    }
}
