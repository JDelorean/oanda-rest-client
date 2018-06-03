package pl.jdev.oanda_rest_client.service.oanda_service.position;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oanda.url.position")
@Data
public class OandaPositionUrl {
    @NotBlank
    private String list;
    @NotBlank
    private String open;
    @NotBlank
    private String single;
    @NotBlank
    private String singleClose;
}
