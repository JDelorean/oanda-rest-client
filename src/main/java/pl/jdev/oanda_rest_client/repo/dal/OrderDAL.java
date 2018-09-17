package pl.jdev.oanda_rest_client.repo.dal;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.domain.order.Order;

import java.util.Collection;

@Component
@Log(topic = "DB - Order")
public class OrderDAL extends DAL<Order> {
    @Override
    public Order get(String extObjectId) {
        return null;
    }

    @Override
    public Collection<Order> getAll() {
        return null;
    }

    @Override
    public Order upsert(String extObjectId, Order upsertObject) {
        return null;
    }

    @Override
    public boolean remove(String extObjectId) {
        return false;
    }

    @Override
    public boolean containsObjectId(String extObjectId) {
        return false;
    }

    @Override
    public boolean containsObjectIds(Collection<String> extObjectIds) {
        return false;
    }
}
