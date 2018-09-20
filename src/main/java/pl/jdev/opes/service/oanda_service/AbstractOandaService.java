package pl.jdev.opes.service.oanda_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes.config.Urls;
import pl.jdev.opes.domain.AbstractEntity;

import java.util.List;
import java.util.stream.Stream;

@Component
@DependsOn("DAO")
public abstract class AbstractOandaService<T extends AbstractEntity> {
    protected MultiValueMap<String, String> headers;
    protected RestTemplate restTemplate;
    protected Urls urls;

    @Autowired
    public AbstractOandaService(MultiValueMap<String, String> headers,
                                RestTemplate restTemplate,
                                Urls urls) {
        this.headers = headers;
        this.restTemplate = restTemplate;
        this.urls = urls;
    }

    //TODO: implement and replace boilerplate. Possibly with Optional?
    private Object[] objectListToArray(List<Object> objects) {
        if (objects == null || objects.size() == 0) {
            return new Object[]{};
        } else {
            return Stream.of(objects)
                    .map(Object.class::cast)
                    .toArray();
        }
    }
}
