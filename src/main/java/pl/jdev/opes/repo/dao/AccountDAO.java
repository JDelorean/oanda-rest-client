package pl.jdev.opes.repo.dao;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pl.jdev.opes.domain.account.Account;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
@Log(topic = "DB - Account")
public class AccountDAO extends DAO<Account> {
    @Override
    public Collection<Account> getAll() {
        log.info("Getting all accounts...");
        Collection<Account> accountList = mongoTemplate.findAll(Account.class);
        log.info(String.format("Returning accounts %s", accountList));
        return accountList;
    }

    public List<Account> getSyncedAccounts() {
        return null;
    }

    @Override
    public Account getByDocumentId(ObjectId documentId) {
        return mongoTemplate.findById(documentId, Account.class);
    }

    @Override
    public Account getById(String accountId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("accountId").is(accountId));
        return mongoTemplate.findOne(query, Account.class);
    }

    @Override
    public void insert(Account object) {

    }

    public void upsert(String objectId, Account upsertObject) {
        Account targetAccount = getById(objectId);
        if (targetAccount == null) {
            log.info(String.format("No account entry with id: %s. Inserting into DB...", objectId));
            mongoTemplate.save(upsertObject);
        } else {
            log.info(String.format("Upserting account %s with %s...", objectId, upsertObject));
            Query query = new Query();
            query.addCriteria(Criteria.where("accountId").is(objectId));
            Update update = new Update();
            Field[] fields = targetAccount.getClass().getDeclaredFields();
            Arrays.stream(fields)
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object newValue = field.get(upsertObject);
                            //TODO: should filter and log only changed field values, not all.
                            log.info(String.format("Updating field '%s' to: %s", field.getName(), newValue));
                            update.set(field.getName(), newValue);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } finally {
                            field.setAccessible(false);
                        }
                    });
            mongoTemplate.upsert(query, update, Account.class);
        }
        Account account = getById(objectId);
        log.info(String.format("Successfully created/updated account %s", account));
    }

    @Override
    public boolean containsObjectIds(Collection<String> id) {
        return false;
    }
}

