package pl.jdev.oanda_rest_client.repo.dao;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.pricing.Price;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

@Repository
@Log(topic = "DB - Pricing")
public class PricingDAO extends DAO<Price> {
    @Override
    public Collection<Price> getAll() {
        return mongoTemplate.findAll(Price.class);
    }

    @Override
    public Price getByDocumentId(ObjectId documentId) {
        return mongoTemplate.findById(documentId, Price.class);
    }

    @Override
    public Price getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("time").is(id));
        return mongoTemplate.findOne(query, Price.class);
    }

    @Override
    public void insert(Price object) {
        mongoTemplate.insert(object);
    }

    @Override
    public void upsert(String objectId, Price upsertObject) {
        Query query = new Query();
        query.addCriteria(Criteria.where("time").is(objectId));
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
        mongoTemplate.upsert(query, update, Price.class);
    }

    @Override
    public boolean containsObjectIds(Collection<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("time").in(ids));
        return mongoTemplate.exists(query, Price.class);
    }
}
