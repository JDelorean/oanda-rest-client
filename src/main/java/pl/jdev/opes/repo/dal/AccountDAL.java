package pl.jdev.opes.repo.dal;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.jdev.opes_commons.domain.account.Account;

import java.util.Collection;

@Component
@Log4j2(topic = "DB - Account")
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
