package pl.jdev.opes.rest.controller;

import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.trade.Trade;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.message.request.EntityDetailsRequest;
import pl.jdev.opes_commons.rest.message.response.JsonTradeListWrapper;
import pl.jdev.opes_commons.rest.message.response.JsonTradeWrapper;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

import static pl.jdev.opes_commons.rest.HttpHeaders.ACTION_TYPE;
import static pl.jdev.opes_commons.rest.HttpHeaders.DATA_TYPE;

@RestController
@RequestMapping("/trades")
public class TradeController extends AbstractEntityController<Trade> {

    @GetMapping
    @ResponseBody
    public JsonTradeListWrapper getAllTrades() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "trade");
        return JsonTradeListWrapper.payloadOf(
                (Collection<Trade>) integrationClient.requestData(
                        new EntityDetailsRequest(),
                        headers,
                        JsonTradeListWrapper.class
                ).getBody()
        );
    }

    @GetMapping("/open")
    @ResponseBody
    public JsonTradeListWrapper getAllOpenTrades() {
        //TODO: need to figure out how to pass extra characteristics of the call
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "trade");
        return JsonTradeListWrapper.payloadOf(
                (Collection<Trade>) integrationClient.requestData(
                        new EntityDetailsRequest(),
                        headers,
                        JsonTradeListWrapper.class
                ).getBody()
        );
    }

    @GetMapping("/{tradeId}")
    public JsonTradeWrapper getTrade(@Valid @PathVariable(name = "tradeId") final UUID tradeId) {
        //TODO: grab extId from db
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "trade");
        return JsonTradeWrapper.payloadOf(
                (Trade) integrationClient.requestData(
                        new EntityDetailsRequest(tradeId, tradeId.toString()),
                        headers,
                        JsonTradeWrapper.class
                ).getBody()
        );
    }

    @PutMapping("/{tradeId}/close")
    public void closeTrade(@Valid @PathVariable(name = "tradeId") final UUID tradeId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ACTION_TYPE, "closeTrade");
//        integrationClient.perform(
//                new CloseTradeRequest(),
//                headers
//        );
    }
}
