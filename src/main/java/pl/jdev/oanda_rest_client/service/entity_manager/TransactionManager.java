package pl.jdev.oanda_rest_client.service.entity_manager;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import pl.jdev.oanda_rest_client.domain.transaction.Transaction;
import pl.jdev.oanda_rest_client.service.IManagerService;

import java.util.Collection;
import java.util.Map;

@Service
@Log
public class TransactionManager implements IManagerService<Transaction> {
    @Override
    public Collection<Transaction> getAll() {
        return getAll(null);
    }

    @Override
    public Collection<Transaction> getAll(Map.Entry<String, String>... parameters) {

        return null;
    }

    @Override
    public Transaction get(ObjectId id) {
        return null;
    }

    @Override
    public Transaction get(String id) {
        return null;
    }

    @Override
    public void create(String object) {

    }

    @Override
    public void create(Transaction object) {

    }

    @Override
    public void update(String object) {

    }

    @Override
    public void update(Transaction object) {

    }
}
