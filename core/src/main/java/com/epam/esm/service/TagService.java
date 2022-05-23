package com.epam.esm.service;

import com.epam.esm.dto.TagRequestDto;
import com.epam.esm.entity.TagEntity;

import java.util.List;

public interface TagService extends BaseService<TagEntity, TagRequestDto> {
    List<TagEntity> getAll();
}
