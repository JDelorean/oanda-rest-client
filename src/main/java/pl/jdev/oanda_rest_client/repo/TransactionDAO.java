package pl.jdev.oanda_rest_client.repo;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.transaction.Transaction;

import java.util.List;

import static java.lang.String.format;

@Repository
@Log(topic = "DB - Transaction")
public class TransactionDAO extends AbstractDAO<Transaction> {
    @Override
    public List<Transaction> getAll() {
        log.info("Getting all transactions...");
        List<Transaction> transactions = template.findAll(Transaction.class);
        log.info(format("Returning transactions %s", transactions));
        return transactions;
    }

    @Override
    public Transaction getByDocumentId(ObjectId documentId) {
        return template.findById(documentId, Transaction.class);
    }

    @Override
    public Transaction getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("transactionId").is(id));
        return template.findOne(query, Transaction.class);
    }

    @Override
    public Transaction upsert(String targetId, Transaction overrides) {
        return null;
    }
}
