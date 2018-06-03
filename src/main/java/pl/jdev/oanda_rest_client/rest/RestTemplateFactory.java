package pl.jdev.oanda_rest_client.rest;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.LastTransactionInterceptor;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.OandaRequestHeaderEnrichmentInterceptor;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {

    private RestTemplate restTemplate;
    @Autowired
    private OandaRequestHeaderEnrichmentInterceptor oandaRequestHeaderEnrichmentInterceptor;
    @Autowired
    private RestLoggingInterceptor restLoggingInterceptor;
    @Autowired
    private LastTransactionInterceptor lastTransactionExtractor;

    @Override
    public RestTemplate getObject() throws Exception {
        return restTemplate;
    }

    @Override
    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        restTemplate = new RestTemplateBuilder().additionalInterceptors(
                this.oandaRequestHeaderEnrichmentInterceptor,
                this.restLoggingInterceptor,
                this.restLoggingInterceptor)
                .build();
    }
}
