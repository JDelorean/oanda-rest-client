package pl.jdev.oanda_rest_client.repo.dal;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;
import pl.jdev.oanda_rest_client.repo.dao.DAO;

import java.util.Collection;

/**
 * Data Access Layer (DAL) objects are database persistance proxy objects
 * which utilize @see pl.jdev.oanda_rest_client.repo.dao.DAO objects
 * and implement methods with logic to optimize performance of database connections.
 * DAL objects should be used as a primary point of database entry.
 */
public abstract class DAL<E extends AbstractEntity> {
    @Autowired
    protected DAO<E> dao;

    public abstract E get(String extObjectId);

    public abstract Collection<E> getAll();

    public abstract E upsert(String extObjectId, E upsertObject);

    public abstract boolean remove(String extObjectId);

    public abstract boolean containsObjectId(String extObjectId);

    public abstract boolean containsObjectIds(Collection<String> extObjectIds);
}
