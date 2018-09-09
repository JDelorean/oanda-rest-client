package pl.jdev.oanda_rest_client.service.oanda_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;

@Component
@DependsOn("AbstractDAO")
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
}
