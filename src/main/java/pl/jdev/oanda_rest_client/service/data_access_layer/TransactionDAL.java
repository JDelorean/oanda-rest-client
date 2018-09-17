package pl.jdev.oanda_rest_client.service.data_access_layer;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.domain.transaction.Transaction;

import java.util.Collection;
import java.util.List;


@Component
@Log(topic = "DB - Transaction")
public class TransactionDAL extends DAL<Transaction> {
    @Override
    public Transaction get(String id) {
        return dao.getById(id);
    }

    @Override
    public Collection<Transaction> getAll() {
        return dao.getAll();
    }

    @Override
    public Transaction upsert(String objectId, Transaction upsertObject) {
        if(!containsObjectId(objectId)){
            log.info(String.format("No transaction entry with id: %s. Inserting into DB...", objectId));
            dao.insert(upsertObject);
        } else {
            log.info(String.format("Upserting transaction %s with %s...", objectId, upsertObject));
            dao.upsert(objectId, upsertObject);
        }
        Transaction transaction = dao.getById(objectId);
        log.info(String.format("Successfully created/updated transaction %s", transaction));
        return transaction;
    }

    @Override
    public boolean remove(String ObjectId) {
        return false;
    }

    @Override
    public boolean containsObjectId(String objectId) {
        return dao.containsObjectIds(List.of(objectId));
    }

    @Override
    public boolean containsObjectIds(Collection<String> objectIds) {
        return dao.containsObjectIds(objectIds);
    }
}
