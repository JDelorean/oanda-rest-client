package pl.jdev.oanda_rest_client.service.oanda_service.transaction;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import pl.jdev.oanda_rest_client.config.OandaAuthConfig;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.transaction.Transaction;
import pl.jdev.oanda_rest_client.domain.transaction.TransactionType;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

import java.util.Date;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Service
@Log
public class OandaTransactionService extends AbstractOandaService<Transaction> {
    @Autowired
    public OandaTransactionService(OandaAuthConfig oandaAuthConfig, Urls urls, RestLoggingInterceptor restLoggingInterceptor, MappingJackson2HttpMessageConverter messageConverter, RestTemplateBuilder restTemplateBuilder) {
        super(oandaAuthConfig, urls, restLoggingInterceptor, messageConverter, restTemplateBuilder);
    }

    public Transaction[] getTransactionList(String accountId, Date fromDate, Date toDate, int pageSize, TransactionType[] transactionTypes) {
        return this.restTemplate
                .exchange(fromPath(urls.TRANSACTION_LIST_URL)
                                .queryParam("from", fromDate)
                                .queryParam("to", toDate)
                                .queryParam("pageSize", pageSize)
                                .queryParam("type", transactionTypes)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        Transaction[].class)
                .getBody();
    }

    public Transaction getTransaction(String accountId, String transactionId) {
        return this.restTemplate
                .exchange(fromPath(urls.SINGLE_TRANSACTION_URL)
                                .build(accountId, transactionId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        Transaction.class)
                .getBody();
    }

    public Transaction[] getTransactionIdRange(String accountId, Integer fromTransaction, Integer toTransaction) {
        return this.restTemplate
                .exchange(fromPath(urls.SINGLE_TRANSACTION_URL)
                                .queryParam("from", fromTransaction)
                                .queryParam("to", toTransaction)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        Transaction[].class)
                .getBody();
    }

    public Transaction[] getTransactionSinceId(String accountId, Integer sinceTransaction) {
        return this.restTemplate
                .exchange(fromPath(urls.SINGLE_TRANSACTION_URL)
                                .queryParam("id", sinceTransaction)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        Transaction[].class)
                .getBody();
    }

    public Transaction subscribeToStream(String accountId) {
        return this.restTemplate
                .exchange(fromPath(urls.TRANSACTION_STREAM_URL)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        Transaction.class)
                .getBody();
    }
}
