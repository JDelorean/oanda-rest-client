package pl.jdev.opes.repo.dao;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.jdev.opes_commons.domain.AbstractEntity;

import java.util.Collection;

public abstract class DAO<E extends AbstractEntity> {
    @Autowired
    protected MongoTemplate mongoTemplate;

    public abstract Collection<E> getAll();

    public abstract E getByDocumentId(ObjectId documentId);

    public abstract E getById(String id);

    public abstract void insert(E object);

    public abstract void upsert(String objectId, E upsertObject);

    public abstract boolean containsObjectIds(Collection<String> ids);
}
