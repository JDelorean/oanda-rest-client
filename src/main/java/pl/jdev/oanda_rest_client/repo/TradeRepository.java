package pl.jdev.oanda_rest_client.repo;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

@Repository
public interface TradeRepository {
    Trade findByTradeId(ObjectId tradeId);

//    List<Trade> findByAccountId(ObjectId accountId);
}