package pl.jdev.oanda_rest_client.repo;

import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.position.Position;

import java.util.List;

import static java.lang.String.format;

@Repository
@Log(topic = "DB - Position")
public class PositionDAO extends AbstractDAO<Position> {
    @Override
    public List<Position> getAll() {
        log.info("Getting all positions...");
        List<Position> positions = template.findAll(Position.class);
        log.info(format("Returning positions %s", positions));
        return positions;
    }

    @Override
    public Position getByDocumentId(ObjectId documentId) {
        return template.findById(documentId, Position.class);
    }

    //TODO: is this really necessary?
    @Override
    public Position getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("positionId").is(id));
        return template.findOne(query, Position.class);
    }

    @Override
    public Position upsert(String targetId, Position overrides) {
        return null;
    }
}
