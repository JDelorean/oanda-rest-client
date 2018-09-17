package pl.jdev.oanda_rest_client.repo.dao;

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
import java.util.Collection;

import static java.lang.String.format;

@Repository
@Log(topic = "DB - Trade")
public class TradeDAO extends DAO<Trade> {
    @Override
    public Collection<Trade> getAll() {
        log.info("Getting all trades...");
        Collection<Trade> trades = mongoTemplate.findAll(Trade.class);
        log.info(format("Returning orders %s", trades));
        return trades;
    }

    @Override
    public Trade getByDocumentId(ObjectId documentId) {
        return mongoTemplate.findById(documentId, Trade.class);
    }

    @Override
    public Trade getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tradeId").is(id));
        return mongoTemplate.findOne(query, Trade.class);
    }

    @Override
    public void insert(Trade object) {

    }

    @Override
    public void upsert(String targetId, Trade overrides) {
        Trade target = getById(targetId);
        if (target == null) {
            log.info(format("No trade entry with id: %s. Inserting into DB...", targetId));
            mongoTemplate.save(overrides);
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
            mongoTemplate.upsert(query, update, Order.class);
        }
        Trade trade = getById(targetId);
        log.info(format("Successfully created/updated trade %s", trade));
    }

    @Override
    public boolean containsObjectIds(Collection<String> id) {
        return false;
    }
}