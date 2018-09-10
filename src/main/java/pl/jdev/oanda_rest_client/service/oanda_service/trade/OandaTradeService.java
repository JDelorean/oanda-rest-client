package pl.jdev.oanda_rest_client.service.oanda_service.trade;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.ClientExtensions;
import pl.jdev.oanda_rest_client.domain.trade.Trade;
import pl.jdev.oanda_rest_client.repo.TradeDAO;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonObjectListWrapper;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;


@Component
@Log
public class OandaTradeService extends AbstractOandaService<Trade> {
    @Autowired
    private TradeDAO repository;

    @Autowired
    public OandaTradeService(MultiValueMap<String, String> headers, RestTemplate restTemplate, Urls urls) {
        super(headers, restTemplate, urls);
    }

    public Object[] getAllTrades(String accountId) {
        List<Object> objects = this.restTemplate
                .exchange(fromPath(urls.TRADE_LIST_URL)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonObjectListWrapper.class)
                .getBody()
                .getObjectList();

        if (objects == null || objects.size() == 0) {
            return new Object[]{};
        } else {
            return Stream.of(objects)
                    .map(Trade.class::cast)
                    .toArray();
        }
    }

    public Object[] getOpenTrades(String accountId) {
        List<Object> objects = this.restTemplate
                .exchange(fromPath(urls.OPEN_TRADE_LIST_URL)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonObjectListWrapper.class)
                .getBody()
                .getObjectList();

        if (objects == null || objects.size() == 0) {
            return new Object[]{};
        } else {
            return Stream.of(objects)
                    .map(Trade.class::cast)
                    .toArray();
        }
    }

    public Trade getTrade(String tradeId) {
        //TODO: implement
        return null;
    }

    public Object closeTrade(String tradeId) {
        //TODO: implement
        return null;
    }

    public Object partiallyCloseTrade(String tradeId, int units) {
        //TODO: implement
        return null;
    }

    public Object updateTradeClientExtensions(String tradeId, ClientExtensions clientExtensions) {
        //TODO: implement
        return null;
    }

    //TODO: implement last endpoint http://developer.oanda.com/rest-live-v20/trade-ep/ if necessary

}
