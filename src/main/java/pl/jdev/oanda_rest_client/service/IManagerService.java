package pl.jdev.oanda_rest_client.service;

import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;

public interface IManagerService<T> {

    Collection<T> getAll();

    Collection<T> getAll(Map.Entry<String, String>... parameters);

    default Map<String, String> removeBadParameters(Map.Entry<String, String>... parameters) {
        if (parameters != null) {
            return Stream.of(parameters)
                    .filter(parameter -> {
                        if (parameter.getKey() == null || parameter.getValue() == null) {
                            return false;
                        } else if (parameter.getKey().isEmpty() || parameter.getValue().isEmpty()) {
                            return false;
                        } else {
                            return true;
                        }
                    })
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return emptyMap();
    }

    T get(ObjectId id);

    T get(String id);

    void create(String object);

    void create(T object);

    void update(String object);

    void update(T object);


}
