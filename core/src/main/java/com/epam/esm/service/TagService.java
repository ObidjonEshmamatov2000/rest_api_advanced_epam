package com.epam.esm.service;

import com.epam.esm.dto.params.TagParams;
import com.epam.esm.dto.request.TagRequestDto;
import com.epam.esm.entity.TagEntity;

import java.util.List;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */
public interface TagService extends BaseService<TagEntity, TagRequestDto> {
    List<TagEntity> findTagsByName(String name);

    List<TagEntity> findMostUsedTagOfUserWithHighestCostOfOrders();

    List<TagEntity> findAllTags(TagParams tagParams);
}
