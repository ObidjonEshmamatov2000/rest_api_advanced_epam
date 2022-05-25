package com.epam.esm.repository;

import com.epam.esm.entity.TagEntity;

import java.util.List;

public interface TagRepository extends CrudRepository<TagEntity, Long> {
    List<TagEntity> findByName(String name);
}
