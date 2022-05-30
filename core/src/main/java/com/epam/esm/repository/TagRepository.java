package com.epam.esm.repository;

import com.epam.esm.entity.TagEntity;

import java.util.List;
import java.util.Map;

public interface TagRepository extends CrudRepository<TagEntity, Long> {
    List<TagEntity> findByName(String name);

    List<TagEntity> findTagsByCertificateId(Integer giftCertificateId, Map<String, Integer> paginationParam);

    List<TagEntity> getAllTags(Map<String, Integer> paginationParam);

    List<TagEntity> findMostUsedTagOfUserWithHighestCostOfOrders();
}
