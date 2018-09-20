package pl.jdev.opes.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "orc.currency")
@Data
public class CurrencyConfig {
    @Value("#{'${orc.currency.supported}'.split(',')}")
    private List<Currency> supportedCurrencies;
//    @Value("default")
//    private Currency defaultCurrency;
}
