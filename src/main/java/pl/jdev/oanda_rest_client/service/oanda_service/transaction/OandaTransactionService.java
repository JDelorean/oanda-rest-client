package pl.jdev.oanda_rest_client.service.oanda_service.transaction;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.transaction.Transaction;
import pl.jdev.oanda_rest_client.domain.transaction.TransactionType;
import pl.jdev.oanda_rest_client.repo.dal.TransactionDAL;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonTransactionListWrapper;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonTransactionRerouteWrapper;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonTransactionWrapper;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;

import java.util.Collection;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Service
@Log
public class OandaTransactionService extends AbstractOandaService<Transaction> {
    @Autowired
    private TransactionDAL repository;

    @Autowired
    public OandaTransactionService(MultiValueMap<String, String> headers,
                                   RestTemplate restTemplate,
                                   Urls urls) {
        super(headers, restTemplate, urls);
    }

    public Collection<Transaction> getTransactionList(String accountId, String fromDate, String toDate, String pageSize, TransactionType[] transactionTypes) {
        log.info(this.restTemplate.toString());
        String rerouteUrl = this.restTemplate
                .exchange(fromPath(urls.TRANSACTION_LIST_URL)
                                .queryParam("from", fromDate)
                                .queryParam("to", toDate)
                                .queryParam("pageSize", pageSize)
                                .queryParam("type", transactionTypes)
                                .buildAndExpand(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonTransactionRerouteWrapper.class)
                .getBody()
                .getPages()
                .get(0);
        //TODO: ^^ will only fetch the first page. Need to provide a solution for multiple pages.
        Collection<Transaction> transactions = this.restTemplate
                .exchange(rerouteUrl,
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonTransactionListWrapper.class)
                .getBody()
                .getTransactions();
        return repository.upsertMulti(transactions);
    }

    public Transaction getTransaction(String accountId, String transactionId) {
        if (repository.containsObjectId(transactionId)) {
            return repository.get(transactionId);
        }
        Transaction transaction = this.restTemplate
                .exchange(fromPath(urls.SINGLE_TRANSACTION_URL)
                                .build(accountId, transactionId),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonTransactionWrapper.class)
                .getBody()
                .getTransaction();
        return repository.upsert(transactionId, transaction);
    }

    public Collection<Transaction> getTransactionsIdRange(String accountId, Integer fromTransaction, Integer toTransaction) {
        return this.restTemplate
                .exchange(fromPath(urls.TRANSACTION_ID_RANGE_URL)
                                .queryParam("from", fromTransaction)
                                .queryParam("to", toTransaction)
                                .build(accountId),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonTransactionListWrapper.class)
                .getBody()
                .getTransactions();
    }

    public Collection<Transaction> getTransactionsSinceId(String accountId, Integer sinceTransaction) {
        return this.restTemplate
                .exchange(fromPath(urls.TRANSACTION_SINCE_ID_URL)
                                .queryParam("id", sinceTransaction)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonTransactionListWrapper.class)
                .getBody().getTransactions();
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
