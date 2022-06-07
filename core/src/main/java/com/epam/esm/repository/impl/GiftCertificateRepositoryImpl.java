package com.epam.esm.repository.impl;

import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.utils.ParamsStringProvider.*;
import static com.epam.esm.utils.ParamsStringProvider.LAST_UPDATE_DATE;

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
    public List<GiftCertificateEntity> findAll(PaginationParams paginationParams) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> cq = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> entityRoot = cq.from(GiftCertificateEntity.class);
        cq.select(entityRoot);
        return entityManager.createQuery(cq)
                .setFirstResult(paginationParams.getPageNumber())
                .setMaxResults(paginationParams.getPageSize())
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
            PaginationParams paginationParams,
            String sortingString
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> cq = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> entityRoot = cq.from(GiftCertificateEntity.class);
        cq.select(entityRoot).where(cb.like(entityRoot.get("name"), "%" + name + "%"));
        if (sortingString != null) {
            List<Order> orderList = buildOrderList(cb, entityRoot, sortingString);
            cq.orderBy(orderList);
        }
        return entityManager
                .createQuery(cq)
                .setFirstResult(paginationParams.getPageNumber())
                .setMaxResults(paginationParams.getPageSize())
                .getResultList();
    }

    private List<Order> buildOrderList(
            CriteriaBuilder cb,
            Root<GiftCertificateEntity> entityRoot,
            String sortingString
    ) {
        List<String> giftCertificateParams = new ArrayList<>();
        giftCertificateParams.add(NAME);
        giftCertificateParams.add(DESCRIPTION);
        giftCertificateParams.add(PRICE);
        giftCertificateParams.add(DURATION);
        giftCertificateParams.add(CREATE_DATE);
        giftCertificateParams.add(LAST_UPDATE_DATE);

        List<Order> orderList = new ArrayList<>();
        String[] strings = sortingString.split(", ");
        for (String s: strings) {
            String[] split = s.split(" ");
            if (split.length == 2) {
                giftCertificateParams.forEach(gc -> {
                    if (split[0].equals(gc)) {
                        if (split[1].equals("asc")) orderList.add(cb.asc(entityRoot.get(gc)));
                        else orderList.add(cb.desc(entityRoot.get(gc)));
                    }
                });
            }
        }
        return orderList;
    }

    @Override
    public List<GiftCertificateEntity> findAllFilteredAndSortedByDescription(
            String description,
            PaginationParams paginationParams,
            String sortingString
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> cq = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> entityRoot = cq.from(GiftCertificateEntity.class);
        cq.select(entityRoot).where(cb.like(entityRoot.get("description"), "%" + description + "%"));
        if (sortingString != null) {
            List<Order> orderList = buildOrderList(cb, entityRoot, sortingString);
            cq.orderBy(orderList);
        }
        return entityManager
                .createQuery(cq)
                .setFirstResult(paginationParams.getPageNumber())
                .setMaxResults(paginationParams.getPageSize())
                .getResultList();
    }

    @Override
    public List<GiftCertificateEntity> findAllFilteredAndSortedByTagNames(
            List<String> tagNameList,
            PaginationParams paginationParams,
            String sortingString
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> cq = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> entityRoot = cq.from(GiftCertificateEntity.class);
        SetJoin<GiftCertificateEntity, TagEntity> tags = entityRoot.joinSet("tags", JoinType.LEFT);
        cq.select(entityRoot).where(tags.get("name").in(tagNameList));
        if (sortingString != null) {
            List<Order> orderList = buildOrderList(cb, entityRoot, sortingString);
            cq.orderBy(orderList);
        }
        return entityManager
                .createQuery(cq)
                .setMaxResults(paginationParams.getPageSize())
                .getResultList();
    }

    @Override
    public List<GiftCertificateEntity> findAllFilteredAndSorted(
            PaginationParams paginationParams,
            String sortingString
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificateEntity> cq = cb.createQuery(GiftCertificateEntity.class);
        Root<GiftCertificateEntity> entityRoot = cq.from(GiftCertificateEntity.class);
        cq.select(entityRoot);
        if (sortingString != null) {
            List<Order> orderList = buildOrderList(cb, entityRoot, sortingString);
            cq.orderBy(orderList);
        }
        return entityManager
                .createQuery(cq)
                .setFirstResult(paginationParams.getPageNumber())
                .setMaxResults(paginationParams.getPageSize())
                .getResultList();
    }
}
