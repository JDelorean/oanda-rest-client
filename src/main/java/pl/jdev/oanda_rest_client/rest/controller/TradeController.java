package pl.jdev.oanda_rest_client.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.oanda_rest_client.domain.ClientExtensions;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonTradeListWrapper;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonTradeWrapper;
import pl.jdev.oanda_rest_client.service.oanda_service.trade.OandaTradeService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts/{accountId}/trades")
public class TradeController {
    @Autowired
    OandaTradeService oandaTradeService;

    @GetMapping
    @ResponseBody
    public JsonTradeListWrapper getAllTrades(@Valid @PathVariable(name = "accountId") final String accountId) {
        return JsonTradeListWrapper.payloadOf(oandaTradeService.getAllTrades(accountId));
    }

    @GetMapping("/open")
    @ResponseBody
    public JsonTradeListWrapper getAllOpenTrades(@Valid @PathVariable(name = "accountId") final String accountId) {
        return JsonTradeListWrapper.payloadOf(oandaTradeService.getOpenTrades(accountId));
    }

    @GetMapping("/{tradeSpecifier}")
    public JsonTradeWrapper getTrade(@Valid @PathVariable(name = "accountId") final String accountId,
                                     @Valid @PathVariable(name = "tradeSpecifier") final String tradeSpecifier) {
        return JsonTradeWrapper.payloadOf(oandaTradeService.getTrade(accountId, tradeSpecifier));
    }

    @PutMapping("/{tradeSpecifier}/close")
    public Map<String, Object> closeTrade(@Valid @PathVariable(name = "accountId") final String accountId,
                                          @Valid @PathVariable(name = "tradeSpecifier") final String tradeSpecifier) {
        return Map.of("trade", oandaTradeService.closeTrade(accountId, tradeSpecifier));
    }

    @PutMapping("/{tradeSpecifier}/clientExtensions")
    public Map<String, Object> updateClientExtensions(@Valid @PathVariable(name = "accountId") final String accountId,
                                                      @Valid @PathVariable(name = "tradeSpecifier") final String tradeSpecifier,
                                                      @Valid @RequestBody final ClientExtensions clientExtensions) {
        return Map.of("trade", oandaTradeService.updateTradeClientExtensions(accountId, tradeSpecifier, clientExtensions));
    }
}
