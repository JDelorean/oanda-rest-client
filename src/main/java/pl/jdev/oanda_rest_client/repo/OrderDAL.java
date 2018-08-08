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
import java.util.List;

@Repository
@Log(topic = "DB - Order")
public class OrderDAL extends AbstractDAL<Order> {
    @Override
    public List<Order> getAll() {
        log.info("Getting all orders for...");
        List<Order> orderList = template.findAll(Order.class);
        log.info(String.format("Returning orders %s", orderList));
        return orderList;
    }

    @Override
    public Order getByDocumentId(ObjectId documentId) {
        return template.findById(documentId, Order.class);
    }

    public Order getByOrderId(String orderId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("orderId").is(orderId));
        return template.findOne(query, Order.class);
    }

    public Order upsert(String targetOrderId, Order orderChanges) {
        Order targetOrder = getByOrderId(targetOrderId);
        if (targetOrder == null) {
            log.info(String.format("No entry with id: %s. Inserting into DB...", targetOrderId));
            template.save(orderChanges);
        } else {
            log.info(String.format("Upserting order %s with %s...", targetOrderId, orderChanges));
            Query query = new Query();
            query.addCriteria(Criteria.where("orderId").is(targetOrderId));
            Update update = new Update();
            Field[] fields = targetOrder.getClass().getDeclaredFields();
            Arrays.stream(fields)
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            Object newValue = field.get(orderChanges);
                            //TODO: should filter and log only changed field values, not all.
                            log.info(String.format("Updating field '%s' to: %s", field.getName(), newValue));
                            update.set(field.getName(), newValue);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } finally {
                            field.setAccessible(false);
                        }
                    });
            template.upsert(query, update, Order.class);
        }
        Order order = getByOrderId(targetOrderId);
        log.info(String.format("Successfully created/updated order %s", order));
        return order;
    }
}