package pl.jdev.oanda_rest_client.service.oanda_service.transaction;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jdev.oanda_rest_client.domain.transaction.Transaction;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.OandaRequestHeaderEnrichmentInterceptor;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

public class OandaTransactionService {

    private RestTemplate restTemplate;
    @Autowired
    private OandaRequestHeaderEnrichmentInterceptor oandaRequestHeaderEnrichmentInterceptor;
    @Autowired
    private RestLoggingInterceptor restLoggingInterceptor;
    @Autowired
    private OandaTransactionUrl oandaTransactionUrl;

    public OandaTransactionService(RestTemplateBuilder restTemplateBuilder,
                                   OandaRequestHeaderEnrichmentInterceptor oandaRequestHeaderEnrichmentInterceptor, RestLoggingInterceptor restLoggingInterceptor) {
        this.oandaRequestHeaderEnrichmentInterceptor = oandaRequestHeaderEnrichmentInterceptor;
        this.restLoggingInterceptor = restLoggingInterceptor;
        this.restTemplate = restTemplateBuilder.additionalInterceptors(this.oandaRequestHeaderEnrichmentInterceptor, this.restLoggingInterceptor)
                .build();
    }

    @SneakyThrows
    public Transaction[] getTransactions(String accountId, Map<String, String> queryParams) {
        UriComponentsBuilder componentBuilder = fromUriString(oandaTransactionUrl.getList());
        queryParams.forEach(componentBuilder::queryParam);
        return restTemplate.getForObject(componentBuilder.buildAndExpand(accountId).encode().toUri(),
                Transaction[].class);
    }

    @SneakyThrows
    public List<Transaction> getPageOfTransactions(String accountId, String from, String to, String pageSize,
                                                   String type) {
        UriComponentsBuilder componentBuilder = fromUriString(oandaTransactionUrl.getList());

        return Arrays.asList(restTemplate.getForObject(componentBuilder.buildAndExpand(accountId).encode().toUri(),
                Transaction[].class));
    }

    @SneakyThrows
    public Transaction getTransaction(String accountId, String transactionId) {
        return restTemplate.getForObject(oandaTransactionUrl.getSingle(), Transaction.class, accountId, transactionId);
    }


}
