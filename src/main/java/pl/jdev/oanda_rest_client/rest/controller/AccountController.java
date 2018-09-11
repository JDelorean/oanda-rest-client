package pl.jdev.oanda_rest_client.rest.controller;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.rest.exception.AccountNotFoundException;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonAccountListWrapper;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonAccountWrapper;
import pl.jdev.oanda_rest_client.service.oanda_service.account.OandaAccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@Log
public class AccountController extends AbstractEntityController<Account> {

    @Autowired
    OandaAccountService oandaAccountService;

    @GetMapping
    @ResponseBody
    public JsonAccountListWrapper getAllAccounts() {
        return JsonAccountListWrapper.payloadOf(oandaAccountService.getAllAccounts());
    }

    //TODO: remove sneakyhrows
    @GetMapping(value = "/{accountId}")
    @ResponseBody
    @SneakyThrows
    public JsonAccountWrapper getAccount(@Valid @PathVariable(required = true) final String accountId) {
        Account account = this.oandaAccountService.getAccount(accountId);
        if (account == null) throw new AccountNotFoundException();
        return JsonAccountWrapper.payloadOf(account);
    }

    @PutMapping(value = "/{accountId}")
    @ResponseBody
    public JsonAccountWrapper syncAccount(@Valid @PathVariable(required = true) final String accountId) {
//        return JsonAccountWrapper.payloadOf();
        return null;
    }


    @DeleteMapping(value = "/{accountId}")
    @ResponseBody
    public JsonAccountWrapper unsyncAccount(@Valid @PathVariable(required = true) final String accountId) {
//        return JsonAccountWrapper.payloadOf(null);
        return null;
    }
}