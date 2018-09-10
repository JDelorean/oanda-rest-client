package pl.jdev.oanda_rest_client.repo;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.order.Order;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Repository
@Log(topic = "DB - Order")
public class TradeDAO extends AbstractDAO<Trade> {
    @Override
    public List<Trade> getAll() {
        log.info("Getting all trades...");
        List<Trade> trades = template.findAll(Trade.class);
        log.info(format("Returning orders %s", trades));
        return trades;
    }

    @Override
    public Trade getByDocumentId(ObjectId documentId) {
        return template.findById(documentId, Trade.class);
    }

    @Override
    public Trade getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tradeId").is(id));
        return template.findOne(query, Trade.class);
    }

    @Override
    public Trade upsert(String targetId, Trade overrides) {
        Trade target = getById(targetId);
        if (target == null) {
            log.info(format("No trade entry with id: %s. Inserting into DB...", targetId));
            template.save(overrides);
        } else {
            log.info(format("Upserting trade %s with %s...", targetId, overrides));
            Query query = new Query();
            query.addCriteria(Criteria.where("tradeId").is(targetId));
            Update update = new Update();
            Field[] fields = target.getClass().getDeclaredFields();
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
            template.upsert(query, update, Order.class);
        }
        Trade trade = getById(targetId);
        log.info(format("Successfully created/updated trade %s", trade));
        return trade;
    }
}