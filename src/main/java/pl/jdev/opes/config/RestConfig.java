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
import pl.jdev.opes_commons.rest.client.AbacusClient;
import pl.jdev.opes_commons.rest.client.IntegrationClient;
import pl.jdev.opes_commons.rest.client.nuntius.NuntiusClient;
import pl.jdev.opes_commons.rest.interceptor.RestLoggingInterceptor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
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
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.of(10000, ChronoUnit.MILLIS))
                .setReadTimeout(Duration.of(10000, ChronoUnit.MILLIS))
                .additionalInterceptors(restInterceptors)
                .additionalMessageConverters(messageConverter)
                .build();
        restTemplate.setRequestFactory(requestFactory());
        return restTemplate;
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
                                        @Value("${opes.integration.port}") String integrationPort,
                                        @Value("${opes.integration.version}") String integrationVersion) {
        return new IntegrationClient(restTemplate, String.format("%s:%s/%s", integrationHostUrl, integrationPort, integrationVersion));
    }

    @Bean
    @DependsOn({"restTemplate"})
    @Autowired
    AbacusClient abacusClient(RestTemplate restTemplate,
                              @Value("${opes.abacus.host}") String abacusHostUrl,
                              @Value("${opes.abacus.port}") String abacusPort,
                              @Value("${opes.abacus.version}") String abacusVersion) {
        return new AbacusClient(restTemplate, String.format("%s:%s/%s", abacusHostUrl, abacusPort, abacusVersion));
    }

    @Bean
    @DependsOn({"restTemplate"})
    @Autowired
    NuntiusClient nuntiusClient(RestTemplate restTemplate,
                                @Value("${opes.nuntius.host}") String nuntiusHostUrl,
                                @Value("${opes.nuntius.port}") String nuntiusPort,
                                @Value("${opes.nuntius.version}") String nuntiusVersion) {
        return new NuntiusClient(restTemplate, String.format("%s:%s/%s", nuntiusHostUrl, nuntiusPort, nuntiusVersion));
    }
}