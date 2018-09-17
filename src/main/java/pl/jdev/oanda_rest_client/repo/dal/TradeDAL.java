package pl.jdev.oanda_rest_client.repo.dal;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

import java.util.Collection;

@Component
@Log(topic = "DB - Trade")
public class TradeDAL extends DAL<Trade>{
    @Override
    public Trade get(String extObjectId) {
        return null;
    }

    @Override
    public Collection<Trade> getAll() {
        return null;
    }

    @Override
    public Trade upsert(String extObjectId, Trade upsertObject) {
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
