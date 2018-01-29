package pl.jdev.oanda_rest_client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.jdev.oanda_rest_client.domain.trade.Trade;
import pl.jdev.oanda_rest_client.repo.TradeRepository;

@RestController
@RequestMapping("/api")
public class TradeController {

    @Autowired
    TradeRepository tradeRepository;

    @GetMapping("/trades")
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    @GetMapping("/trades/{id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable(value = "id") Long tradeId) {
        Trade trade = tradeRepository.findOne(tradeId);
        if (trade == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(trade);
    }
}