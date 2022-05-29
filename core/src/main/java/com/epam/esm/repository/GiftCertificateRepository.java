package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificateEntity;

import java.util.List;
import java.util.Map;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificateEntity, Long> {
    List<GiftCertificateEntity> findByName(String name);

    List<GiftCertificateEntity> findAllFilteredAndSortedByName(String name, Map<String, Integer> paginationParam, String sortingString);

    List<GiftCertificateEntity> findAllFilteredAndSortedByDescription(String description, Map<String, Integer> paginationParam, String sortingString);

    List<GiftCertificateEntity> findAllFilteredAndSortedByTagNames(List<String> tagNameList, Map<String, Integer> paginationParam, String sortingString);

    List<GiftCertificateEntity> findAllFilteredAndSorted(Map<String, Integer> paginationParam, String validSortingString);
}
