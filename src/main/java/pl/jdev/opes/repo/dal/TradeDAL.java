package pl.jdev.opes.repo.dal;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.jdev.opes_commons.domain.trade.Trade;

import java.util.Collection;

@Component
@Log4j2(topic = "DB - Trade")
public class TradeDAL extends DAL<Trade> {
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
