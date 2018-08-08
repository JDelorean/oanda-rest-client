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
public class AccountDAL extends AbstractDAL<Account> {
    @Override
    public List<Account> getAll() {
        log.info("Getting all accounts...");
        List<Account> accountList = template.findAll(Account.class);
        log.info(String.format("Returning accounts %s", accountList));
        return accountList;
    }

    @Override
    public Account getByDocumentId(ObjectId documentId) {
        return template.findById(documentId, Account.class);
    }

    public Account getByAccountId(String accountId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("accountId").is(accountId));
        return template.findOne(query, Account.class);
    }

    public Account upsert(String targetAccountId, Account accountChanges) {
        Account targetAccount = getByAccountId(targetAccountId);
        if (targetAccount == null) {
            log.info(String.format("No entry with id: %s. Inserting into DB...", targetAccountId));
            template.save(accountChanges);
        } else {
            log.info(String.format("Upserting account %s with %s...", targetAccountId, accountChanges));
            Query query = new Query();
            query.addCriteria(Criteria.where("accountId").is(targetAccountId));
            Update update = new Update();
            Field[] fields = targetAccount.getClass().getDeclaredFields();
            Arrays.stream(fields)
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object newValue = field.get(accountChanges);
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
        Account account = getByAccountId(targetAccountId);
        log.info(String.format("Successfully created/updated account %s", account));
        return getByAccountId(targetAccountId);
    }
}

