package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    @Override
    public GiftCertificateEntity create(GiftCertificateEntity obj) {
        return null;
    }

    @Override
    public List<GiftCertificateEntity> getAll(int limit, int offset) {
        return null;
    }

    @Override
    public GiftCertificateEntity findById(Long aLong) {
        return null;
    }

    @Override
    public GiftCertificateEntity update(GiftCertificateEntity obj) {
        return null;
    }

    @Override
    public int delete(Long aLong) {
        return 0;
    }
}
