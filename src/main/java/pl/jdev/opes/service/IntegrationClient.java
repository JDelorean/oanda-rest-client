package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes.integration.message.DataRequest;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
public class IntegrationClient extends HttpService {

    @Value("${opes.integration.host}")
    String integrationHost;

    public IntegrationClient(MultiValueMap headers, RestTemplate restTemplate) {
        super(headers, restTemplate);
    }

    public ResponseEntity<?> requestData(DataRequest dataRequest) {
        return requireNonNull(this.restTemplate
                .exchange(fromPath(integrationHost)
                                .path("data")
                                .build()
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        ResponseEntity.class)
                .getBody());
    }
}
