package pl.jdev.opes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes.service.TradeService;
import pl.jdev.opes_commons.domain.trade.Trade;
import pl.jdev.opes_commons.domain.trade.TradeState;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/trades")
public class TradeController extends AbstractEntityController<Trade> {

    @Autowired
    private TradeService tradeService;

    @GetMapping
    @ResponseBody
    public Set<Trade> getAllTrades(@RequestParam(required = false) TradeState state) {
        return tradeService.getAllTrades(state);
    }

    @GetMapping("/{tradeId}")
    public Trade getTrade(@Valid @PathVariable(name = "tradeId") final UUID tradeId) throws NotFoundException {
        return tradeService.getTrade(tradeId);
    }

    @PutMapping("/{tradeId}/close")
    @ResponseStatus(HttpStatus.OK)
    public void closeTrade(@Valid @PathVariable(name = "tradeId") final UUID tradeId) throws NotFoundException {
        tradeService.closeTrade(tradeId);
    }
}
