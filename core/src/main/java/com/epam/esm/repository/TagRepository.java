package com.epam.esm.repository;

import com.epam.esm.dto.params.PaginationParams;
import com.epam.esm.entity.TagEntity;

import java.util.List;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface TagRepository extends CrudRepository<TagEntity, Long> {
    List<TagEntity> findByName(String name);

    List<TagEntity> findTagsByCertificateId(Integer giftCertificateId, PaginationParams paginationParams);

    List<TagEntity> findMostUsedTagOfUserWithHighestCostOfOrders();
}
