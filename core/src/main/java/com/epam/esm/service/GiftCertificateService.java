package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateRequestDto;
import com.epam.esm.entity.GiftCertificateEntity;
import org.springframework.validation.BindingResult;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

public interface GiftCertificateService extends BaseService<GiftCertificateEntity, GiftCertificateRequestDto> {
    GiftCertificateEntity update(GiftCertificateRequestDto update, Long certificateId, BindingResult bindingResult);
}
