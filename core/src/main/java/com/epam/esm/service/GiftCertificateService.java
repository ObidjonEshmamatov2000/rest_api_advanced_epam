package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;
import org.springframework.validation.BindingResult;

public interface GiftCertificateService extends BaseService<GiftCertificateEntity, GiftCertificateRequestDto> {
    GiftCertificateEntity update(GiftCertificateRequestDto update, Long certificateId, BindingResult bindingResult);
}
