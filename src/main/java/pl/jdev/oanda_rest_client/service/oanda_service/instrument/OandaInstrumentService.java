package pl.jdev.oanda_rest_client.service.oanda_service.instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import pl.jdev.oanda_rest_client.config.OandaAuthConfig;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

public class OandaInstrumentService extends AbstractOandaService<Instrument> {

    @Autowired
    public OandaInstrumentService(OandaAuthConfig oandaAuthConfig, Urls urls, RestLoggingInterceptor restLoggingInterceptor, MappingJackson2HttpMessageConverter messageConverter, RestTemplateBuilder restTemplateBuilder) {
        super(oandaAuthConfig, urls, restLoggingInterceptor, messageConverter, restTemplateBuilder);
    }

    public Instrument[] getAll() {
        return new Instrument[0];
    }

    public Instrument get(String id) {
        return null;
    }
}
