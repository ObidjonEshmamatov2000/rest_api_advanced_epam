package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TagEntity create(TagEntity obj) {
        entityManager.persist(obj);
        if (obj.getId() != null)
            return obj;
        return null;
    }

    @Override
    public List<TagEntity> getAll(int limit, int offset) {
        return entityManager.createQuery("select t from TagEntity t", TagEntity.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public TagEntity findById(Long id) {
        return entityManager.find(TagEntity.class, id);
    }

    @Override
    public TagEntity update(TagEntity obj) {
        return entityManager.merge(obj);
    }

    @Override
    public int delete(Long id) {
        return entityManager.createQuery("delete from TagEntity where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<TagEntity> findByName(String name) {
        return entityManager.createQuery("select t from TagEntity t where t.name = :name", TagEntity.class)
                .setParameter("name", name)
                .getResultList();
    }
}
