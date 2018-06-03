package pl.jdev.oanda_rest_client.rest.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.domain.account.AccountDTO;
import pl.jdev.oanda_rest_client.rest.converter.AccountConverter;
import pl.jdev.oanda_rest_client.service.IManagerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@JsonIgnoreProperties
@Log
public class AccountController {

    @Autowired
    AccountConverter accountConverter;
    @Autowired
    IManagerService<Account> accountManager;

    @GetMapping
    @ResponseBody
    public List<AccountDTO> getAllAccounts() {
        return accountConverter.createFromEntities(accountManager.getAll());
    }

    @GetMapping(value = "/{documentId}", params = "idType=document")
    @ResponseBody
    public AccountDTO getAccount(@Valid @PathVariable(required = true) ObjectId documentId) {
        return accountConverter.createFrom(accountManager.get(documentId));
    }

    @GetMapping(value = "/{accountId}", params = "idType=account")
    @ResponseBody
    public AccountDTO getAccount(@Valid @PathVariable(required = true) String accountId) {
        return accountConverter.createFrom(accountManager.get(accountId));
    }

}
