package pl.jdev.opes.service.oanda_service.trade;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes.config.Urls;
import pl.jdev.opes.domain.ClientExtensions;
import pl.jdev.opes.domain.trade.Trade;
import pl.jdev.opes.rest.json.wrapper.JsonTradeListWrapper;
import pl.jdev.opes.rest.json.wrapper.JsonTradeWrapper;
import pl.jdev.opes.repo.dal.TradeDAL;
import pl.jdev.opes.service.oanda_service.AbstractOandaService;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;


@Component
@Log(topic = "CORE - Trade")
public class OandaTradeService extends AbstractOandaService<Trade> {
    @Autowired
    private TradeDAL repository;

    @Autowired
    public OandaTradeService(MultiValueMap<String, String> headers, RestTemplate restTemplate, Urls urls) {
        super(headers, restTemplate, urls);
    }

    public List<Trade> getAllTrades(String accountId) {
        return this.restTemplate
                .exchange(fromPath(urls.TRADE_LIST_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonTradeListWrapper.class)
                .getBody()
                .getTrades()
                .stream()
                .map(trade -> repository.upsert(trade.getTradeId(), trade))
                .collect(toList());
    }

    public List<Trade> getOpenTrades(String accountId) {
        return this.restTemplate
                .exchange(fromPath(urls.OPEN_TRADE_LIST_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonTradeListWrapper.class)
                .getBody()
                .getTrades()
                .stream()
                .map(trade -> repository.upsert(trade.getTradeId(), trade))
                .collect(toList());
    }

    public Trade getTrade(String accountId, String tradeId) {
        return Stream.of(this.restTemplate
                .exchange(fromPath(urls.SINGLE_TRADE_URL)
                                .buildAndExpand(accountId, tradeId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonTradeWrapper.class)
                .getBody()
                .getTrade())
                .map(trade -> repository.upsert(trade.getTradeId(), trade))
                .findFirst()
                .get();
    }

    public Object closeTrade(String accountId, String tradeId) {
        return this.restTemplate
                .exchange(fromPath(urls.CLOSE_TRADE_URL)
                                .buildAndExpand(accountId, tradeId)
                                .toString(),
                        PUT,
                        new HttpEntity<>(EMPTY, this.headers),
                        Trade.class)
                .getBody();
    }

    public Object partiallyCloseTrade(String accountId, String tradeId, int units) {
        //TODO: implement
        return null;
    }

    public Object updateTradeClientExtensions(String accountId, String tradeId, ClientExtensions clientExtensions) {
        //TODO: implement
        return null;
    }

    //TODO: implement last endpoint http://developer.oanda.com/rest-live-v20/trade-ep/ if necessary

}
