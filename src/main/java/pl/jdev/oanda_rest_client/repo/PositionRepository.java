package pl.jdev.oanda_rest_client.repo;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pl.jdev.oanda_rest_client.domain.position.Position;

@Repository
public interface PositionRepository extends MongoRepository<Position, UUID> {
}
