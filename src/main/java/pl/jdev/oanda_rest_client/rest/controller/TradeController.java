package pl.jdev.oanda_rest_client.rest.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jdev.oanda_rest_client.domain.trade.Trade;
import pl.jdev.oanda_rest_client.repo.TradeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    TradeRepository tradeRepository;

    @GetMapping
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Trade> getTradeById(@PathVariable(value = "id") final ObjectId tradeId) {
        Trade trade = tradeRepository.findByTradeId(tradeId);
        if (trade == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(trade);
    }
}