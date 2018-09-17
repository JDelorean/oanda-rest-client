package pl.jdev.oanda_rest_client.service.data_access_layer;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;
import pl.jdev.oanda_rest_client.repo.DAO;

import java.util.Collection;

/**
 * Data Access Layer (DAL) objects are database persistance proxy objects
 * which utilize @see pl.jdev.oanda_rest_client.repo.DAO objects
 * and implement methods with logic to optimize performance of database connections.
 * DAL objects should be used as a primary point of database entry.
 */
public abstract class DAL<E extends AbstractEntity> {
    @Autowired
    protected DAO<E> dao;

    public abstract E get(String id);

    public abstract Collection<E> getAll();

    public abstract E upsert(String objectId, E upsertObject);

    public abstract boolean remove(String ObjectId);

    public abstract boolean containsObjectId(String objectId);

    public abstract boolean containsObjectIds(Collection<String> objectIds);
}
