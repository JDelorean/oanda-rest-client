package pl.jdev.oanda_rest_client.repo;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.order.Order;

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
    public void upsert(String targetId, Order overrides) {
        Order targetOrder = getById(targetId);
        if (targetOrder == null) {
            log.info(format("No order entry with id: %s. Inserting into DB...", targetId));
            mongoTemplate.save(overrides);
        } else {
            log.info(format("Upserting order %s with %s...", targetId, overrides));
            Query query = new Query();
            query.addCriteria(Criteria.where("orderId").is(targetId));
            Update update = new Update();
            Field[] fields = targetOrder.getClass().getDeclaredFields();
            Arrays.stream(fields)
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object newValue = field.get(overrides);
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
        Order order = getById(targetId);
        log.info(format("Successfully created/updated order %s", order));
    }

    @Override
    public boolean containsObjectIds(Collection<String> id) {
        return false;
    }
}