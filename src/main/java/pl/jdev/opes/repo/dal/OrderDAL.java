package pl.jdev.opes.repo.dal;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.jdev.opes_commons.domain.order.Order;

import java.util.Collection;

@Component
@Log4j2(topic = "DB - Order")
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
