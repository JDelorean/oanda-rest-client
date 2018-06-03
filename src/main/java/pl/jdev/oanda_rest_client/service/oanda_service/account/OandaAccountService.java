package pl.jdev.oanda_rest_client.service.oanda_service.account;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.domain.transaction.ClientConfigureTransaction;
import pl.jdev.oanda_rest_client.service.IOandaService;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.OandaRequestHeaderEnrichmentInterceptor;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class OandaAccountService implements IOandaService<Account> {

    private RestTemplate restTemplate;
    @Autowired
    private OandaRequestHeaderEnrichmentInterceptor oandaRequestHeaderEnrichmentInterceptor;
    @Autowired
    private RestLoggingInterceptor restLoggingInterceptor;
    @Autowired
    private OandaAccountUrl oandaAccountUrl;

    public OandaAccountService(RestTemplateBuilder restTemplateBuilder,
                               OandaRequestHeaderEnrichmentInterceptor oandaRequestHeaderEnrichmentInterceptor,
                               RestLoggingInterceptor restLoggingInterceptor) {
        this.oandaRequestHeaderEnrichmentInterceptor = oandaRequestHeaderEnrichmentInterceptor;
        this.restLoggingInterceptor = restLoggingInterceptor;
        this.restTemplate = restTemplateBuilder.additionalInterceptors(this.oandaRequestHeaderEnrichmentInterceptor, this.restLoggingInterceptor)
                .build();
    }

    @SneakyThrows
    public Collection<Account> getAllAccounts() {
        Account[] accounts;
        accounts = restTemplate.getForObject(oandaAccountUrl.getAll(), Account[].class);
        return List.of(accounts);
    }

    //    @SneakyThrows
//    @Async
//    public CompletableFuture<Account> getAccount(String accountId) {
//        return CompletableFuture.completedFuture(restTemplate.getForObject(oandaAccountUrl.getSingle(), Account.class, accountId));
//    }
    @SneakyThrows
    public Account getAccount(String accountId) {
        return restTemplate.getForObject(oandaAccountUrl.getSingle(), Account.class, accountId);
    }

    @SneakyThrows
    public Account getAccountSummary(String accountId) {
        return restTemplate.getForObject(oandaAccountUrl.getSummary(), Account.class, accountId);
    }

    @SneakyThrows
    public Account getAccountInstruments(String accountId, String instruments) {
        UriComponentsBuilder componentBuilder = UriComponentsBuilder.fromUriString(oandaAccountUrl.getInstruments());
        if (instruments != null) {
            String csvInstruments = String.join(",", Arrays.asList(instruments));
            componentBuilder.queryParam("instruments", csvInstruments);
        }
        return restTemplate.getForObject(componentBuilder.buildAndExpand(accountId).encode().toUri(), Account.class);
    }

    // public void setAccountAlias(String accountId, String alias) {
    // // TODO impl
    // }
    //
    // public void setAccountMarginRate(String accountId, long marginRate) {
    // // TODO impl
    // }

    public ClientConfigureTransaction patchAccountConfig(String accountId, String body) {
        return null;
    }

    @SneakyThrows
    public Account getAccountChanges(String accountId, String transactionId) {
        UriComponentsBuilder componentBuilder = UriComponentsBuilder.fromUriString(oandaAccountUrl.getChanges());
        if (transactionId != null) {
            componentBuilder.queryParam("sinceTransactionID", transactionId);
        }
        return restTemplate.getForObject(componentBuilder.buildAndExpand(accountId).encode().toUri(), Account.class);
    }

}
