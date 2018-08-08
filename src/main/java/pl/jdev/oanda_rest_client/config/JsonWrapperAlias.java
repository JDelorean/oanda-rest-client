package pl.jdev.oanda_rest_client.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:json.wrapper.alias.yml")
@Getter
public class JsonWrapperAlias {
    //    @Value("${Account.singular}")
    public String ACCOUNT_SINGULAR;
    //    @Value("${Account.plural}")
    public String ACCOUNT_PLURAL;
}
