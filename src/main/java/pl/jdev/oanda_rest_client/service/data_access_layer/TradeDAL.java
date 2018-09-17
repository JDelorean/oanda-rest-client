package pl.jdev.oanda_rest_client.service.data_access_layer;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

import java.util.Collection;

@Component
@Log(topic = "DB - Trade")
public class TradeDAL extends DAL<Trade>{
    @Override
    public Trade get(String id) {
        return null;
    }

    @Override
    public Collection<Trade> getAll() {
        return null;
    }

    @Override
    public Trade upsert(String objectId, Trade upsertObject) {
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
