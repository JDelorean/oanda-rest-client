package pl.jdev.oanda_rest_client.repo;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;

import java.util.Collection;

public abstract class DAO<E extends AbstractEntity> {
    @Autowired
    protected MongoTemplate mongoTemplate;

    public abstract Collection<E> getAll();

    public abstract E getByDocumentId(ObjectId documentId);

    public abstract E getById(String id);

    public abstract void insert(E object);

    public abstract void upsert(String targetId, E overrides);

    public abstract boolean containsObjectIds(Collection<String> ids);
}
