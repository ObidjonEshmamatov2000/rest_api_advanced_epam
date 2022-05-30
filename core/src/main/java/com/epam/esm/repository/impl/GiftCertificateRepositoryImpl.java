package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.utils.ParamsStringProvider;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificateEntity create(GiftCertificateEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public List<GiftCertificateEntity> getAll(int limit, int offset) {
        String query = "select gc from GiftCertificateEntity gc";
        return entityManager.createQuery(query, GiftCertificateEntity.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public GiftCertificateEntity findById(Long aLong) {
        return entityManager.find(GiftCertificateEntity.class, aLong);
    }

    @Override
    public GiftCertificateEntity update(GiftCertificateEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public int delete(Long aLong) {
        return entityManager.createQuery("delete from GiftCertificateEntity where id = :id")
                .setParameter("id", aLong)
                .executeUpdate();
    }

    @Override
    public List<GiftCertificateEntity> findByName(String name) {
        String query = "select gc from GiftCertificateEntity gc where gc.name =: name";
        return entityManager.createQuery(query, GiftCertificateEntity.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<GiftCertificateEntity> findAllFilteredAndSortedByName(String name, Map<String, Integer> paginationParam, String sortingString) {
        String query = "select gc from GiftCertificateEntity gc where gc.name like CONCAT('%', ?1, '%')";
        return entityManager.createQuery(
                        buildSortingQuery(query, sortingString),
                        GiftCertificateEntity.class
                )
                .setParameter(1, name)
                .setFirstResult(paginationParam.get(ParamsStringProvider.OFFSET))
                .setMaxResults(paginationParam.get(ParamsStringProvider.LIMIT))
                .getResultList();
    }

    private String buildSortingQuery(String query, String sortingString) {
        return sortingString.isEmpty() ? query : query + " order by " + sortingString;
    }

    @Override
    public List<GiftCertificateEntity> findAllFilteredAndSortedByDescription(String description, Map<String, Integer> paginationParam, String sortingString) {
        String query = "select gc from GiftCertificateEntity gc where gc.description like CONCAT('%', ?1, '%')";
        return entityManager.createQuery(
                buildSortingQuery(query, sortingString),
                GiftCertificateEntity.class
        )
                .setParameter(1, description)
                .setFirstResult(paginationParam.get(ParamsStringProvider.OFFSET))
                .setMaxResults(paginationParam.get(ParamsStringProvider.LIMIT))
                .getResultList();
    }

    @Override
    public List<GiftCertificateEntity> findAllFilteredAndSortedByTagNames(List<String> tagNameList, Map<String, Integer> paginationParam, String sortingString) {
        String query = "select g FROM GiftCertificateEntity g JOIN FETCH g.tags t";
        String tagNameQuery = buildTagNameQuery(tagNameList, query);
        return entityManager.createQuery(
                        buildSortingQuery(tagNameQuery, sortingString),
                        GiftCertificateEntity.class
                )
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
    public List<GiftCertificateEntity> findAllFilteredAndSorted(Map<String, Integer> paginationParam, String sortingString) {
        String query = "select gc from GiftCertificateEntity gc";
        return entityManager.createQuery(
                        buildSortingQuery(query, sortingString),
                        GiftCertificateEntity.class
                )
                .setFirstResult(paginationParam.get(ParamsStringProvider.OFFSET))
                .setMaxResults(paginationParam.get(ParamsStringProvider.LIMIT))
                .getResultList();
    }
}
