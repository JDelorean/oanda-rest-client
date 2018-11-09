package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jdev.opes_commons.rest.message.DataRequest;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.POST;

@Component
public class IntegrationClient extends HttpService {

    @Value("${opes.integration.host}")
    private String integrationHost;

    public IntegrationClient(MultiValueMap headers, RestTemplate restTemplate) {
        super(headers, restTemplate);
    }

    public ResponseEntity requestData(DataRequest dataRequest) {
        headers.add("Data-Type", "sma");
        return requireNonNull(this.restTemplate
                .exchange(UriComponentsBuilder.newInstance()
                                .scheme(ReferenceUriSchemesSupported.HTTP.toString())
                                .host(integrationHost)
                                .path("/integration/data")
                                .build()
                                .toString(),
                        POST,
                        new HttpEntity<>(dataRequest, this.headers),
                        ResponseEntity.class)
                .getBody());
    }
}