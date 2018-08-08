package pl.jdev.oanda_rest_client.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.order.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, ObjectId> {
}