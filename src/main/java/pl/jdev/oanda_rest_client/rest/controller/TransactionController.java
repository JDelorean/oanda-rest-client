package pl.jdev.oanda_rest_client.rest.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.oanda_rest_client.domain.transaction.Transaction;
import pl.jdev.oanda_rest_client.domain.transaction.TransactionDTO;
import pl.jdev.oanda_rest_client.rest.converter.TransactionConverter;
import pl.jdev.oanda_rest_client.rest.validation.SupportedTransactionType;
import pl.jdev.oanda_rest_client.service.IManagerService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collection;

import static java.util.Map.entry;

@RestController
@RequestMapping("/api/transactions")
@JsonIgnoreProperties
@Log
public class TransactionController {
    @Autowired
    TransactionConverter transactionConverter;
    @Autowired
    IManagerService<Transaction> transactionManager;

    @GetMapping(value = "/{transactionId}", params = "idType=transaction")
    @ResponseBody
    public TransactionDTO getTransactin(@Valid @PathVariable(required = true) String transactionId) {
        return transactionConverter.createFrom(transactionManager.get(transactionId));
    }

    @GetMapping
    @ResponseBody
    public Collection<TransactionDTO> getTransactions(@RequestParam(value = "from", required = false) String from,
                                                      @RequestParam(value = "to", required = false) String to,
                                                      @Min(1) @Max(1000) @RequestParam(value = "pageSize", required = false) String pageSize,
                                                      @SupportedTransactionType @RequestParam(value = "transactionType", required = false) String transactionType) {
        return transactionConverter.createFromEntities(transactionManager.getAll(
                entry("from", from),
                entry("to", to),
                entry("pageSize", pageSize),
                entry("transactionType", transactionType))
        );
    }
}
