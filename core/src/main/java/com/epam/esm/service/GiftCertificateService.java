package com.epam.esm.service;

import com.epam.esm.dto.request.GiftCertificateRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface GiftCertificateService extends BaseService<GiftCertificateEntity, GiftCertificateRequestDto> {
    GiftCertificateEntity update(GiftCertificateRequestDto update, Long certificateId);
}
