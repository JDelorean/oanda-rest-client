package pl.jdev.oanda_rest_client.service.oanda_service.account;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.config.OandaAuthConfig;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.repo.AccountDAO;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonAccountListWrapper;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonAccountWrapper;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

import java.util.List;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log
public class OandaAccountService extends AbstractOandaService<Account> {
    @Autowired
    private AccountDAO repository;

    @Autowired
    public OandaAccountService(OandaAuthConfig oandaAuthConfig, Urls urls, RestLoggingInterceptor restLoggingInterceptor, MappingJackson2HttpMessageConverter messageConverter, RestTemplateBuilder restTemplateBuilder) {
        super(oandaAuthConfig, urls, restLoggingInterceptor, messageConverter, restTemplateBuilder);
    }

    public List<Account> getAllAccounts() {
        this.restTemplate
                .exchange(urls.ACCOUNT_LIST_URL,
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonAccountListWrapper.class)
                .getBody()
                .getAccounts()
                .forEach(account -> this.getAccount(account.getAccountId()));
        return repository.getAll();
    }

    public Account getAccount(String id) {
        Account account = this.restTemplate
                .exchange(fromPath(urls.SINGLE_ACCOUNT_URL).build(id).getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonAccountWrapper.class)
                .getBody()
                .getAccount();
        return repository.upsert(id, account);
    }
}
