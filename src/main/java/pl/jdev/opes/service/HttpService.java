package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public abstract class HttpService {
    protected MultiValueMap<String, String> headers;
    protected RestTemplate restTemplate;

    @Autowired
    public HttpService(MultiValueMap headers, RestTemplate restTemplate) {
        this.headers = headers;
        this.restTemplate = restTemplate;
    }
}
