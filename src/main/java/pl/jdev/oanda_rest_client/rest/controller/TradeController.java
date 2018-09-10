package pl.jdev.oanda_rest_client.rest.controller;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @GetMapping
    public List<Trade> getAllTrades() {
        return null;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Trade> getTradeById(@PathVariable(value = "id") final ObjectId tradeId) {
        return null;
    }
}
