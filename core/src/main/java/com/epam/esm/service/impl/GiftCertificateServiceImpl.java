package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.ApplicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final ApplicationValidator validator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository, ApplicationValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public GiftCertificateEntity create(GiftCertificateRequestDto request) {
        return null;
    }

    @Override
    public GiftCertificateEntity get(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public GiftCertificateEntity update(GiftCertificateRequestDto update, Long certificateId) {
        return null;
    }
}
