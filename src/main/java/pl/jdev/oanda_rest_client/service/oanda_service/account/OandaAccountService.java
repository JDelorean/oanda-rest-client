package pl.jdev.oanda_rest_client.service.oanda_service.account;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.repo.AccountDAO;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonAccountListWrapper;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonAccountWrapper;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;

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
    public OandaAccountService(MultiValueMap<String, String> headers,
                               RestTemplate restTemplate,
                               Urls urls) {
        super(headers, restTemplate, urls);
    }

    public List<Account> getAllAccounts() {
        this.restTemplate
                .exchange(urls.ACCOUNT_LIST_URL,
                        GET,
                        new HttpEntity<>(EMPTY, headers),
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
                        new HttpEntity<>(EMPTY, headers),
                        JsonAccountWrapper.class)
                .getBody()
                .getAccount();
        return repository.upsert(id, account);
    }
}
