package pl.jdev.oanda_rest_client.repo.dao;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.position.Position;

import java.util.Collection;

import static java.lang.String.format;

@Repository
@Log(topic = "DB - Position")
public class PositionDAO extends DAO<Position> {
    @Override
    public Collection<Position> getAll() {
        log.info("Getting all positions...");
        Collection<Position> positions = mongoTemplate.findAll(Position.class);
        log.info(format("Returning positions %s", positions));
        return positions;
    }

    @Override
    public Position getByDocumentId(ObjectId documentId) {
        return mongoTemplate.findById(documentId, Position.class);
    }

    //TODO: is this really necessary?
    @Override
    public Position getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("positionId").is(id));
        return mongoTemplate.findOne(query, Position.class);
    }

    @Override
    public void insert(Position object) {

    }

    @Override
    public void upsert(String objectId, Position upsertObject) {
    }

    @Override
    public boolean containsObjectIds(Collection<String> id) {
        return false;
    }
}
