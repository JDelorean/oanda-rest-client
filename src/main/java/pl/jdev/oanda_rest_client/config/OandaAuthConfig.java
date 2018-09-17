package pl.jdev.oanda_rest_client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.String.join;

@Configuration
public class OandaAuthConfig {
    @Value("${opes.request.auth.prefix}")
    private String prefix;
    @Value("${opes.request.auth.token}")
    private String token;

    @Bean
    String auth() {
        return join(" ", prefix, token);
    }
}
