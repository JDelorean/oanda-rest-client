package pl.jdev.opes.service.oanda_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes.config.Urls;
import pl.jdev.opes.domain.AbstractEntity;
import pl.jdev.opes.service.HttpService;

import java.util.List;
import java.util.stream.Stream;

@Component
@DependsOn("DAO")
public abstract class AbstractOandaService<T extends AbstractEntity> extends HttpService {
    protected Urls urls;

    @Autowired
    public AbstractOandaService(MultiValueMap<String, String> headers,
                                @Qualifier("oanda") RestTemplate restTemplate,
                                Urls urls) {
        super(headers, restTemplate);
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
