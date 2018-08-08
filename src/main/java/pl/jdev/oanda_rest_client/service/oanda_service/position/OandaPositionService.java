package pl.jdev.oanda_rest_client.service.oanda_service.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import pl.jdev.oanda_rest_client.config.OandaAuthConfig;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.position.Position;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

public class OandaPositionService extends AbstractOandaService<Position> {
    @Autowired
    public OandaPositionService(OandaAuthConfig oandaAuthConfig, Urls urls, RestLoggingInterceptor restLoggingInterceptor, MappingJackson2HttpMessageConverter messageConverter, RestTemplateBuilder restTemplateBuilder) {
        super(oandaAuthConfig, urls, restLoggingInterceptor, messageConverter, restTemplateBuilder);
    }

    public Position[] getAll() {
        return new Position[0];
    }

    public Position get(String id) {
        return null;
    }
}
