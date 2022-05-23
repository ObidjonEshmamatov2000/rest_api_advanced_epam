package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {
    @Override
    public TagEntity create(TagEntity obj) {
        return null;
    }

    @Override
    public List<TagEntity> getAll(int limit, int offset) {
        return null;
    }

    @Override
    public TagEntity findById(Long aLong) {
        return null;
    }

    @Override
    public TagEntity update(TagEntity obj) {
        return null;
    }

    @Override
    public int delete(Long aLong) {
        return 0;
    }

    @Override
    public List<TagEntity> findByName() {
        return null;
    }
}
