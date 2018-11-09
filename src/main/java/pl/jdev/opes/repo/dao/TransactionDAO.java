package pl.jdev.opes.repo.dao;

import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pl.jdev.opes_commons.domain.transaction.Transaction;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import static java.lang.String.format;

@Repository
@Log4j2(topic = "DB - Transaction")
public class TransactionDAO extends DAO<Transaction> {
    @Override
    public Collection<Transaction> getAll() {
        log.info("Getting all transactions...");
        Collection<Transaction> transactions = mongoTemplate.findAll(Transaction.class);
        log.info(format("Returning transactions %s", transactions));
        return transactions;
    }

    @Override
    public Transaction getByDocumentId(ObjectId documentId) {
        return mongoTemplate.findById(documentId, Transaction.class);
    }

    @Override
    public Transaction getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("transactionId").is(id));
        return mongoTemplate.findOne(query, Transaction.class);
    }

    @Override
    public void insert(Transaction transaction) {
        mongoTemplate.insert(transaction);
    }

    @Override
    public void upsert(String objectId, Transaction upsertObject) {
        Query query = new Query();
        query.addCriteria(Criteria.where("transactionId").is(objectId));
        Update update = new Update();
        Field[] fields = upsertObject.getClass().getDeclaredFields();
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
        mongoTemplate.upsert(query, update, Transaction.class);
    }

    @Override
    public boolean containsObjectIds(Collection<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("transactionId").in(ids));
        return mongoTemplate.exists(query, Transaction.class);
    }
}
