package pl.jdev.opes.service.oanda_service.account;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes.config.Urls;
import pl.jdev.opes.domain.account.Account;
import pl.jdev.opes.repo.dal.AccountDAL;
import pl.jdev.opes.rest.json.wrapper.JsonAccountListWrapper;
import pl.jdev.opes.rest.json.wrapper.JsonAccountWrapper;
import pl.jdev.opes.service.oanda_service.AbstractOandaService;

import java.util.Collection;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log(topic = "CORE - Account")
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
                .exchange(fromPath(urls.SINGLE_ACCOUNT_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, headers),
                        JsonAccountWrapper.class)
                .getBody()
                .getAccount();
        return repository.upsert(accountId, account);
    }
}
