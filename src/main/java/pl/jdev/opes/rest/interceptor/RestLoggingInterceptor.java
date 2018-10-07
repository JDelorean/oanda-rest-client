package pl.jdev.opes.rest.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import static java.lang.String.format;

@Component
@Log4j2(topic = "REST")
public class RestLoggingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private synchronized void logRequest(HttpRequest request, byte[] body) {
        log.info("======================REQUEST BEGIN======================");
        log.info(format("URI         : %s", request.getURI()));
        log.info(format("Method      : %s", request.getMethod()));
        log.info(format("Headers     : %s", request.getHeaders().toSingleValueMap()));
        log.info(format("Request Body: %s", new String(body)));
        log.info("======================REQUEST END========================");
    }

    private synchronized void logResponse(ClientHttpResponse response) throws IOException {
        log.info("======================RESPONSE BEGIN=====================");
        log.info(format("Status Code  : %s", response.getStatusCode()));
        log.info(format("Status Text  : %s", response.getStatusText()));
        log.info(format("Headers      : %s", response.getHeaders().toSingleValueMap()));
        log.info(format("Response Body: %s", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset())));
        log.info("======================RESPONSE END=======================");
    }
}
