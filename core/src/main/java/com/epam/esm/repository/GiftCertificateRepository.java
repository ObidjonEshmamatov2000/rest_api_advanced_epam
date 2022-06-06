package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificateEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface GiftCertificateRepository extends CrudRepository<GiftCertificateEntity, Long> {
    List<GiftCertificateEntity> findByName(String name);

    List<GiftCertificateEntity> findAllFilteredAndSortedByName(
            String name,
            Map<String, Integer> paginationParam,
            String sortingString
    );

    List<GiftCertificateEntity> findAllFilteredAndSortedByDescription(
            String description,
            Map<String, Integer> paginationParam,
            String sortingString
    );

    List<GiftCertificateEntity> findAllFilteredAndSortedByTagNames(
            List<String> tagNameList,
            Map<String, Integer> paginationParam,
            String sortingString
    );

    List<GiftCertificateEntity> findAllFilteredAndSorted(
            Map<String, Integer> paginationParam,
            String validSortingString
    );
}
