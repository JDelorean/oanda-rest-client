package pl.jdev.opes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.rest.IntegrationClient;
import pl.jdev.opes_commons.rest.interceptor.RestLoggingInterceptor;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class RestConfig {

    @Bean
    @DependsOn({"requestFactory"})
    @Autowired
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                              List<ClientHttpRequestInterceptor> restInterceptors,
                              MappingJackson2HttpMessageConverter messageConverter) {
        RestTemplate rt = restTemplateBuilder
                .additionalInterceptors(restInterceptors)
                .additionalMessageConverters(messageConverter)
                .build();
        rt.setRequestFactory(requestFactory());
        return rt;
    }

    @Bean
    ClientHttpRequestFactory requestFactory() {
        return new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
    }

    @Bean
    RestLoggingInterceptor restLoggingInterceptor() {
        return new RestLoggingInterceptor();
    }

    @Bean
    Map.Entry contentTypeHeader() {
        return Map.entry(CONTENT_TYPE, APPLICATION_JSON);
    }

    @Bean
    @DependsOn({"restTemplate"})
    @Autowired
    IntegrationClient integrationClient(RestTemplate restTemplate,
                                        @Value("${opes.integration.host}") String integrationHostUrl,
                                        @Value("${opes.integration.version}") String integrationVersion) {
        return new IntegrationClient(restTemplate, integrationHostUrl + integrationVersion);
    }
}