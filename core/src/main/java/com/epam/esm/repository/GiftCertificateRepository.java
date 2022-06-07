package com.epam.esm.repository;

import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.entity.GiftCertificateEntity;

import java.util.List;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface GiftCertificateRepository extends CrudRepository<GiftCertificateEntity, Long> {
    List<GiftCertificateEntity> findByName(String name);

    List<GiftCertificateEntity> findAllFilteredAndSortedByName(
            String name,
            PaginationParams paginationParams,
            String sortingString
    );

    List<GiftCertificateEntity> findAllFilteredAndSortedByDescription(
            String description,
            PaginationParams paginationParams,
            String sortingString
    );

    List<GiftCertificateEntity> findAllFilteredAndSortedByTagNames(
            List<String> tagNameList,
            PaginationParams paginationParams,
            String sortingString
    );

    List<GiftCertificateEntity> findAllFilteredAndSorted(
            PaginationParams paginationParams,
            String validSortingString
    );
}
