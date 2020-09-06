package pl.jdev.oanda_rest_client.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class RestConfig {
    @Autowired
    private ApplicationContext ctx;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @DependsOn({"baseUrl", "requestFactory"})
    @Autowired
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                                     List<ClientHttpRequestInterceptor> restInterceptors,
                                     MappingJackson2HttpMessageConverter messageConverter) {
        RestTemplate rt = restTemplateBuilder
                .rootUri((String) ctx.getBean("baseUrl"))
                .additionalInterceptors(restInterceptors)
                .messageConverters(messageConverter)
                .build();
        rt.setRequestFactory((ClientHttpRequestFactory) ctx.getBean("requestFactory"));
        return rt;
    }

    @Bean
    public ClientHttpRequestFactory requestFactory() {
        return new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
    }

    @Bean
    @DependsOn({"auth"})
    public MultiValueMap<String, String> headers() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap();
        headers.add(AUTHORIZATION, (String) ctx.getBean("auth"));
        headers.add(CONTENT_TYPE, APPLICATION_JSON.toString());
        return headers;
    }
}