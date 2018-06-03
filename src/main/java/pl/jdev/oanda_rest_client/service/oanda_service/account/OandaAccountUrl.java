package pl.jdev.oanda_rest_client.service.oanda_service.account;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "oanda.url.account")
public class OandaAccountUrl {
    @NotBlank
    private String all;
    @NotBlank
    private String single;
    @NotBlank
    private String summary;
    @NotBlank
    private String instruments;
    @NotBlank
    private String config;
    @NotBlank
    private String changes;
}
