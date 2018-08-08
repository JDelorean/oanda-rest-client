package pl.jdev.oanda_rest_client.rest.converter;

import pl.jdev.oanda_rest_client.domain.AbstractDto;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public interface EntityDTOConverter<E extends AbstractEntity, D extends AbstractDto> {
    E createFrom(D dto);

    D createFrom(E entity);

    E updateEntity(E entity, D dto);

    default List<D> createFromEntities(final Collection<E> entities) {
        return entities.stream()
                .map(this::createFrom)
                .collect(toList());
    }

    default List<E> createFromDtos(final Collection<D> dtos) {
        return dtos.stream()
                .map(this::createFrom)
                .collect(toList());
    }
}
