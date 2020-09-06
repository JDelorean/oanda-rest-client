package pl.jdev.oanda_rest_client.repo;

import org.bson.types.ObjectId;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;

import java.util.List;

public interface GenericDAO<E extends AbstractEntity> {
    List<E> getAll();

    E getByDocumentId(ObjectId documentId);

    E getById(String id);

    E upsert(String targetId, E overrides);
}
