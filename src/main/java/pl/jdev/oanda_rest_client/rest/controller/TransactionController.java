package pl.jdev.oanda_rest_client.rest.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.oanda_rest_client.domain.transaction.TransactionType;
import pl.jdev.oanda_rest_client.rest.validation.SupportedTransactionType;
import pl.jdev.oanda_rest_client.service.oanda_service.transaction.OandaTransactionService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts/{accountId}/transactions")
@Log
public class TransactionController {
    @Autowired
    OandaTransactionService oandaTransactionService;

    @GetMapping(value = "/{transactionId}")
    @ResponseBody
    public Map<String, Object> getTransaction(@Valid @PathVariable(name = "accountId") final String accountId,
                                              @Valid @PathVariable(required = true) String transactionId) {
        return Map.of("transaction", oandaTransactionService.getTransaction(accountId, transactionId));
    }

    @GetMapping
    @ResponseBody
    public Map<String, Object> getTransactionList(@Valid @PathVariable(name = "accountId") final String accountId,
                                                  @RequestParam(value = "from", required = false) final String from,
                                                  @RequestParam(value = "to", required = false) final String to,
                                                  @Min(1) @Max(1000) @RequestParam(value = "pageSize", required = false) final String pageSize,
                                                  @SupportedTransactionType @RequestParam(value = "transactionTypes", required = false) final TransactionType[] transactionTypes) {
        return Map.of("transactions", oandaTransactionService.getTransactionList(accountId, Date.valueOf(from), Date.valueOf(to), Integer.valueOf(pageSize), transactionTypes));
    }

    @GetMapping("/idrange")
    @ResponseBody
    public Map<String, Object> getTransactionIdrange(@Valid @PathVariable(name = "accountId") final String accountId,
                                                     @Min(1) @RequestParam(value = "from", required = false) final Integer from,
                                                     @RequestParam(value = "to", required = false) final Integer to) {
        return Map.of("transactions", oandaTransactionService.getTransactionIdRange(accountId, from, to));
    }

    @GetMapping("/sinceid")
    @ResponseBody
    public Map<String, Object> getTransactionIdrange(@Valid @PathVariable(name = "accountId") final String accountId,
                                                     @Min(1) @RequestParam(value = "id", required = false) final Integer id) {
        return Map.of("transactions", oandaTransactionService.getTransactionSinceId(accountId, id));
    }
}
