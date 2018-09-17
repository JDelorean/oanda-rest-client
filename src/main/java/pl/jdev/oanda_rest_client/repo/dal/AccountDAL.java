package pl.jdev.oanda_rest_client.repo.dal;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.domain.account.Account;

import java.util.Collection;

@Component
@Log(topic = "DB - Account")
public class AccountDAL extends DAL<Account> {
    @Override
    public Account get(String extObjectId) {
        return null;
    }

    @Override
    public Collection<Account> getAll() {
        return null;
    }

    @Override
    public Account upsert(String extObjectId, Account upsertObject) {
        return null;
    }

    @Override
    public boolean remove(String extObjectId) {
        return false;
    }

    @Override
    public boolean containsObjectId(String extObjectId) {
        return false;
    }

    @Override
    public boolean containsObjectIds(Collection<String> extObjectIds) {
        return false;
    }
}
