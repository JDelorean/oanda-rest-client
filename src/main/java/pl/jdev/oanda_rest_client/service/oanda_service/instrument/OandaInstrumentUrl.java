package pl.jdev.oanda_rest_client.service.oanda_service.instrument;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oanda.url.instrument")
@Data
public class OandaInstrumentUrl {
    @NotBlank
    private String candles;
    @NotBlank
    private String orderBook;
    @NotBlank
    private String positionBook;
}
