package pl.jdev.opes.repo.dao;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pl.jdev.opes.domain.order.Order;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import static java.lang.String.format;

@Repository
@Log(topic = "DB - Order")
public class OrderDAO extends DAO<Order> {
    @Override
    public Collection<Order> getAll() {
        log.info("Getting all orders...");
        Collection<Order> orders = mongoTemplate.findAll(Order.class);
        log.info(format("Returning orders %s", orders));
        return orders;
    }

    @Override
    public Order getByDocumentId(ObjectId documentId) {
        return mongoTemplate.findById(documentId, Order.class);
    }

    @Override
    public Order getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("orderId").is(id));
        return mongoTemplate.findOne(query, Order.class);
    }

    @Override
    public void insert(Order object) {

    }

    @Override
    public void upsert(String objectId, Order upsertObject) {
        Order targetOrder = getById(objectId);
        if (targetOrder == null) {
            log.info(format("No order entry with id: %s. Inserting into DB...", objectId));
            mongoTemplate.save(upsertObject);
        } else {
            log.info(format("Upserting order %s with %s...", objectId, upsertObject));
            Query query = new Query();
            query.addCriteria(Criteria.where("orderId").is(objectId));
            Update update = new Update();
            Field[] fields = targetOrder.getClass().getDeclaredFields();
            Arrays.stream(fields)
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object newValue = field.get(upsertObject);
                            //TODO: should filter and log only changed field values, not all.
                            log.info(format("Updating field '%s' to: %s", field.getName(), newValue));
                            update.set(field.getName(), newValue);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } finally {
                            field.setAccessible(false);
                        }
                    });
            mongoTemplate.upsert(query, update, Order.class);
        }
        Order order = getById(objectId);
        log.info(format("Successfully created/updated order %s", order));
    }

    @Override
    public boolean containsObjectIds(Collection<String> id) {
        return false;
    }
}