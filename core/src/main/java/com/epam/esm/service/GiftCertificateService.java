package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;

public interface GiftCertificateService extends BaseService<GiftCertificateEntity, GiftCertificateRequestDto> {
    GiftCertificateEntity update(GiftCertificateRequestDto update, Long certificateId);
}
