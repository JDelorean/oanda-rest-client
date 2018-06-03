package pl.jdev.oanda_rest_client.service.oanda_service.transaction;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oanda.url.transaction")
@Data
public class OandaTransactionUrl {
    @NotBlank
    private String list;
    @NotBlank
    private String single;
    @NotBlank
    private String idRange;
    @NotBlank
    private String sinceId;
    @NotBlank
    private String stream;
}
