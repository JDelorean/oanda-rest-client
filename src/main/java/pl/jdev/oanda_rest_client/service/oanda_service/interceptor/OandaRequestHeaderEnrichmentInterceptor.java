package pl.jdev.oanda_rest_client.service.oanda_service.interceptor;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@Log
public class OandaRequestHeaderEnrichmentInterceptor implements ClientHttpRequestInterceptor {

    private String authTokenPrefix;
    private String authTokenValue;

    public OandaRequestHeaderEnrichmentInterceptor(@Value("${orc.request.auth_token.prefix}") String authTokenPrefix,
                                                   @Value("${orc.request.auth_token.value}") String authTokenValue) {
        this.authTokenPrefix = authTokenPrefix;
        this.authTokenValue = authTokenValue;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.add(AUTHORIZATION, String.join(" ", authTokenPrefix, authTokenValue));
        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }

}
