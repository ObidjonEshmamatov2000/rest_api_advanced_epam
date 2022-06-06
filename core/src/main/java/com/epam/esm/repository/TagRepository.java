package com.epam.esm.repository;

import com.epam.esm.entity.TagEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface TagRepository extends CrudRepository<TagEntity, Long> {
    List<TagEntity> findByName(String name);

    List<TagEntity> findTagsByCertificateId(Integer giftCertificateId, Map<String, Integer> paginationParam);

    List<TagEntity> findMostUsedTagOfUserWithHighestCostOfOrders();
}
