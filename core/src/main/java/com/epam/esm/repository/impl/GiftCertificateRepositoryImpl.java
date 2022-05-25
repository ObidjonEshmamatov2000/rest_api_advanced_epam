package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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
}
