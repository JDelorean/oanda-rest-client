package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import pl.jdev.opes.db.dto.metadata.Taggable;
import pl.jdev.opes_commons.rest.exception.NotFoundException;

abstract class TaggableEntityService<E extends Taggable, I extends Object> {
    @Autowired
    CrudRepository<E, I> repository;

    public void tagEntity(I entityId, String tag) throws NotFoundException, IllegalStateException {
        E entity = repository.findById(entityId)
                .orElseThrow(NotFoundException::new);
        try {
            entity.tagWith(tag);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(String.format("Failed to tag entity %s with tag '%s'!", entityId, tag));
        }
    }

    public void removeEntityTag(I entityId, String tag) throws NotFoundException {
        E entity = repository.findById(entityId)
                .orElseThrow(NotFoundException::new);
        try {
            entity.removeTag(tag);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(String.format("Failed to remove tag %s from entity %s!", tag, entityId));
        }
    }
}