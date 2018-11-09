package pl.jdev.opes.rest.controller;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.rest.exception.AccountNotFoundException;
import pl.jdev.opes.rest.json.wrapper.JsonAccountListWrapper;
import pl.jdev.opes.rest.json.wrapper.JsonAccountWrapper;
import pl.jdev.opes.service.oanda_service.account.OandaAccountService;
import pl.jdev.opes_commons.domain.account.Account;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@Log4j2
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
    public JsonAccountWrapper getAccount(@Valid @PathVariable final String accountId) {
        Account account = this.oandaAccountService.getAccount(accountId);
        if (account == null) throw new AccountNotFoundException();
        return JsonAccountWrapper.payloadOf(account);
    }

    @PutMapping(value = "/{accountId}")
    @ResponseBody
    public JsonAccountWrapper syncAccount(@Valid @PathVariable final String accountId) {
//        return JsonAccountWrapper.payloadOf();
        return null;
    }


    @DeleteMapping(value = "/{accountId}")
    @ResponseBody
    public JsonAccountWrapper unsyncAccount(@Valid @PathVariable final String accountId) {
//        return JsonAccountWrapper.payloadOf(null);
        return null;
    }
}