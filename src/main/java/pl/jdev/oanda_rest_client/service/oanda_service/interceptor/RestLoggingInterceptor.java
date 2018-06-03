package pl.jdev.oanda_rest_client.service.oanda_service.interceptor;

import lombok.extern.java.Log;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;

@Component
@Log
public class RestLoggingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info(String.format("\n%s %s\nHeaders:\n%s\nBody:\n%s", request.getMethod(), request.getURI(), request.getHeaders().toSingleValueMap(), Arrays.toString(body)));
        ClientHttpResponse response = execution.execute(request, body);
        log.info(String.format("\n%s %s\nStatus: %s %s\n%s", request.getMethod(), request.getURI(), response.getStatusCode(), response.getStatusText(), new BufferedReader(new InputStreamReader(response.getBody())).lines()
                .collect(joining("\n"))));
        return response;
    }

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info(String.join("\n", request.getMethod(), request.getRequestURI(), request.getHeaders().toSingleValueMap(), Arrays.toString(body)));
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
}
