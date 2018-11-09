package pl.jdev.opes.rest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.rest.json.wrapper.JsonTransactionListWrapper;
import pl.jdev.opes.rest.json.wrapper.JsonTransactionWrapper;
import pl.jdev.opes.rest.validation.SupportedTransactionType;
import pl.jdev.opes.service.oanda_service.transaction.OandaTransactionService;
import pl.jdev.opes_commons.domain.transaction.TransactionType;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/accounts/{accountId}/transactions")
@Log4j2
public class TransactionController {
    @Autowired
    OandaTransactionService oandaTransactionService;

    @GetMapping(value = "/{transactionId}")
    @ResponseBody
    public JsonTransactionWrapper getTransaction(@Valid @PathVariable(name = "accountId") final String accountId,
                                                 @Valid @PathVariable final String transactionId) {
        return JsonTransactionWrapper.payloadOf(oandaTransactionService.getTransaction(accountId, transactionId));
    }

    @GetMapping
    @ResponseBody
    public JsonTransactionListWrapper getTransactionList(@Valid @PathVariable(name = "accountId") final String accountId,
                                                         @RequestParam(value = "from", required = false) final String from,
                                                         @RequestParam(value = "to", required = false) final String to,
                                                         @Min(1) @Max(1000) @RequestParam(value = "pageSize", required = false) final String pageSize,
                                                         @SupportedTransactionType @RequestParam(value = "transactionTypes", required = false) final TransactionType[] transactionTypes) {
        return JsonTransactionListWrapper.payloadOf(oandaTransactionService.getTransactionList(accountId, from, to, pageSize, transactionTypes));
    }

    @GetMapping("/idrange")
    @ResponseBody
    public JsonTransactionListWrapper getTransactionListIdRange(@Valid @PathVariable(name = "accountId") final String accountId,
                                                                @Min(1) @RequestParam(value = "from") final Integer from,
                                                                @RequestParam(value = "to") final Integer to) {
        return JsonTransactionListWrapper.payloadOf(oandaTransactionService.getTransactionsIdRange(accountId, from, to));
    }

    @GetMapping("/sinceid")
    @ResponseBody
    public JsonTransactionListWrapper getTransactionListSinceId(@Valid @PathVariable(name = "accountId") final String accountId,
                                                                @Min(1) @RequestParam(value = "id") final Integer id) {
        return JsonTransactionListWrapper.payloadOf(oandaTransactionService.getTransactionsSinceId(accountId, id));
    }
}
