package pl.jdev.oanda_rest_client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Bean("baseUrl")
    @Profile("development")
    String devBaseUrl() {
        return "https://api-fxpractice.oanda.com/v3";
    }

    @Bean("baseUrl")
    @Profile("production")
    String prodBaseUrl() {
        return "https://api-fxtrade.oanda.com/v3";
    }
}
