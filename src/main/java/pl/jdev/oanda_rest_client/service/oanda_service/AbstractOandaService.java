package pl.jdev.oanda_rest_client.service.oanda_service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.OandaAuthConfig;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

import static java.lang.String.join;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public abstract class AbstractOandaService<T extends AbstractEntity> {
    protected MultiValueMap<String, String> headers;
    protected RestTemplate restTemplate;
    protected Urls urls;

    @Autowired
    public AbstractOandaService(OandaAuthConfig oandaAuthConfig,
                                Urls urls,
                                RestLoggingInterceptor restLoggingInterceptor,
                                MappingJackson2HttpMessageConverter messageConverter,
                                RestTemplateBuilder restTemplateBuilder) {
        ClientHttpRequestFactory requestFactory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        headers = new LinkedMultiValueMap();
        headers.add(AUTHORIZATION, join(" ", oandaAuthConfig.getPrefix(), oandaAuthConfig.getToken()));
        headers.add(CONTENT_TYPE, APPLICATION_JSON.toString());
        this.urls = urls;
        this.restTemplate = restTemplateBuilder
                .rootUri(urls.PRACTICE)
                .additionalInterceptors(restLoggingInterceptor)
                .messageConverters(messageConverter)
                .build();
        this.restTemplate.setRequestFactory(requestFactory);
    }
}
