package pl.jdev.oanda_rest_client.repo;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.account.Account;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Repository
@Log(topic = "DB - Account")
public class AccountDAO extends AbstractDAO<Account> {
    @Override
    public List<Account> getAll() {
        log.info("Getting all accounts...");
        List<Account> accountList = template.findAll(Account.class);
        log.info(String.format("Returning accounts %s", accountList));
        return accountList;
    }

    public List<Account> getSyncedAccounts() {
        return null;
    }

    @Override
    public Account getByDocumentId(ObjectId documentId) {
        return template.findById(documentId, Account.class);
    }

    @Override
    public Account getById(String accountId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("accountId").is(accountId));
        return template.findOne(query, Account.class);
    }

    public Account upsert(String targetId, Account overrides) {
        Account targetAccount = getById(targetId);
        if (targetAccount == null) {
            log.info(String.format("No account entry with id: %s. Inserting into DB...", targetId));
            template.save(overrides);
        } else {
            log.info(String.format("Upserting account %s with %s...", targetId, overrides));
            Query query = new Query();
            query.addCriteria(Criteria.where("accountId").is(targetId));
            Update update = new Update();
            Field[] fields = targetAccount.getClass().getDeclaredFields();
            Arrays.stream(fields)
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object newValue = field.get(overrides);
                            //TODO: should filter and log only changed field values, not all.
                            log.info(String.format("Updating field '%s' to: %s", field.getName(), newValue));
                            update.set(field.getName(), newValue);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } finally {
                            field.setAccessible(false);
                        }
                    });
            template.upsert(query, update, Account.class);
        }
        Account account = getById(targetId);
        log.info(String.format("Successfully created/updated account %s", account));
        return getById(targetId);
    }
}

