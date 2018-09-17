package pl.jdev.oanda_rest_client.service.data_access_layer;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.domain.order.Order;

import java.util.Collection;

@Component
@Log(topic = "DB - Order")
public class OrderDAL extends DAL<Order> {
    @Override
    public Order get(String id) {
        return null;
    }

    @Override
    public Collection<Order> getAll() {
        return null;
    }

    @Override
    public Order upsert(String objectId, Order upsertObject) {
        return null;
    }

    @Override
    public boolean remove(String ObjectId) {
        return false;
    }

    @Override
    public boolean containsObjectId(String objectId) {
        return false;
    }

    @Override
    public boolean containsObjectIds(Collection<String> objectIds) {
        return false;
    }
}
