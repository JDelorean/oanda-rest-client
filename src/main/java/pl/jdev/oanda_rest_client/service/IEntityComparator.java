package pl.jdev.oanda_rest_client.service;

import pl.jdev.oanda_rest_client.domain.AbstractEntity;

import java.util.Collection;
import java.util.stream.Stream;

public interface IEntityComparator<E extends AbstractEntity> {
    default boolean compare(E entity, E otherEntity) {
        return entity.equals(otherEntity);
    }

    default boolean compareAll(Collection<E> entities, Collection<E> otherEntities) {
        return entities.size() == Stream.of(entities, otherEntities).distinct().count();
    }
}
