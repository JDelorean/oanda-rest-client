package pl.jdev.oanda_rest_client.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

@Repository
public interface TradeRepository extends MongoRepository<Trade, ObjectId> {
    Trade findByTradeId(ObjectId tradeId);

//    List<Trade> findByAccountId(ObjectId accountId);
}