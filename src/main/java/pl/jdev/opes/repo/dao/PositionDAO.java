package pl.jdev.opes.repo.dao;

import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pl.jdev.opes_commons.domain.position.Position;

import java.util.Collection;

import static java.lang.String.format;

@Repository
@Log4j2(topic = "DB - Position")
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
