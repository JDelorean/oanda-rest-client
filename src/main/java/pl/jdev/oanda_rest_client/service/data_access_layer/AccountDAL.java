package pl.jdev.oanda_rest_client.service.data_access_layer;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.domain.account.Account;

import java.util.Collection;

@Component
@Log(topic = "DB - Account")
public class AccountDAL extends DAL<Account> {
    @Override
    public Account get(String id) {
        return null;
    }

    @Override
    public Collection<Account> getAll() {
        return null;
    }

    @Override
    public Account upsert(String objectId, Account upsertObject) {
        return null;
    }

    @Override
    public boolean remove(String ObjectId) {
        return false;
    }

    @Override
    public boolean containsObjectId(String objectId) {
        return false;
    }

    @Override
    public boolean containsObjectIds(Collection<String> objectIds) {
        return false;
    }
}
