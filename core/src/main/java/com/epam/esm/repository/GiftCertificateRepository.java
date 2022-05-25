package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificateEntity;

import java.util.List;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificateEntity, Long> {
    List<GiftCertificateEntity> findByName(String name);
}
