package pl.jdev.opes.repo.dal;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.jdev.opes_commons.domain.transaction.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Log4j2(topic = "DB - Transaction")
public class TransactionDAL extends DAL<Transaction> {
    @Override
    public Transaction get(String extObjectId) {
        return dao.getById(extObjectId);
    }

    @Override
    public Collection<Transaction> getAll() {
        return dao.getAll();
    }

    @Override
    public Transaction upsert(String extObjectId, Transaction upsertObject) {
        if (!containsObjectId(extObjectId)) {
            log.info(String.format("No transaction entry with id: %s. Inserting into DB...", extObjectId));
            dao.insert(upsertObject);
        } else {
            log.info(String.format("Upserting transaction %s with %s...", extObjectId, upsertObject));
            dao.upsert(extObjectId, upsertObject);
        }
        Transaction transaction = dao.getById(extObjectId);
        log.info(String.format("Successfully created/updated transaction %s", transaction));
        return transaction;
    }

    public Collection<Transaction> upsertMulti(Collection<Transaction> upsertObjects) {
        Map<String, Transaction> transactionMap = upsertObjects.stream()
                .collect(Collectors.toMap(Transaction::getTransactionId, transaction -> transaction));
        transactionMap.forEach(this::upsert);
        return getAll();
    }

    @Override
    public boolean remove(String extObjectId) {
        return false;
    }

    @Override
    public boolean containsObjectId(String extObjectId) {
        return dao.containsObjectIds(List.of(extObjectId));
    }

    @Override
    public boolean containsObjectIds(Collection<String> extObjectIds) {
        return dao.containsObjectIds(extObjectIds);
    }
}
