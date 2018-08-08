package pl.jdev.oanda_rest_client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "orc.request.auth")
public class OandaAuthConfig {
    private String prefix;
    protected String token;
}
