package pl.jdev.oanda_rest_client.service.oanda_service.account;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonAccountListWrapper;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonAccountWrapper;
import pl.jdev.oanda_rest_client.service.data_access_layer.AccountDAL;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;

import java.util.Collection;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log
public class OandaAccountService extends AbstractOandaService<Account> {
    @Autowired
    private AccountDAL repository;

    @Autowired
    public OandaAccountService(MultiValueMap<String, String> headers,
                               RestTemplate restTemplate,
                               Urls urls) {
        super(headers, restTemplate, urls);
    }

    /**
     * Returns all accounts and their details.
     *
     * @return all accounts with details
     */
    public Collection<Account> getAllAccounts() {
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

    /**
     * Returns accounts and its details for the provided accountId.
     *
     * @return accounts with details for provided id
     */
    public Account getAccount(String accountId) {
        Account account = this.restTemplate
                .exchange(fromPath(urls.SINGLE_ACCOUNT_URL).build(accountId).getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, headers),
                        JsonAccountWrapper.class)
                .getBody()
                .getAccount();
        return repository.upsert(accountId, account);
    }
}
