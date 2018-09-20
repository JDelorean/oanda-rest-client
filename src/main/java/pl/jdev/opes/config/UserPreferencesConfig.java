package pl.jdev.opes.config;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collection;

@Configuration
@PropertySource("classpath:default.user.preferences")
@Log
public class UserPreferencesConfig {

    @Bean
    public String accountId(@Value("${account.id}") String accountId) {
        return accountId;
    }

    @Bean
    public Collection<String> currencyPairs(@Value("#{'${currency.pairs}'.split(',')}") Collection<String> currencyPairs) {
        return currencyPairs;
    }
}
