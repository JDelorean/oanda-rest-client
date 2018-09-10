package pl.jdev.oanda_rest_client.rest.controller;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.rest.exception.AccountNotFoundException;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonObjectWrapper;
import pl.jdev.oanda_rest_client.service.oanda_service.account.OandaAccountService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@Log
public class AccountController extends AbstractRestController<Account> {

    @Autowired
    OandaAccountService oandaAccountService;

    @GetMapping
    @ResponseBody
    public Map<String, Object> getAllAccounts() {
        return Map.of("accounts", oandaAccountService.getAllAccounts());
    }

    //TODO: remove sneakyhrows
    @GetMapping(value = "/{accountId}")
    @ResponseBody
    @SneakyThrows
    public JsonObjectWrapper<Account> getAccount(@Valid @PathVariable(required = true) final String accountId) {
        Account account = this.oandaAccountService.getAccount(accountId);
        if (account == null) throw new AccountNotFoundException();
        return new JsonObjectWrapper<>(account);
    }

    @PutMapping(value = "/{accountId}")
    @ResponseBody
    public JsonObjectWrapper<Account> syncAccount(@Valid @PathVariable(required = true) final String accountId) {
        return new JsonObjectWrapper<>(null);
    }

    @DeleteMapping(value = "/{accountId}")
    @ResponseBody
    public JsonObjectWrapper<Account> unsyncAccount(@Valid @PathVariable(required = true) final String accountId) {
        return new JsonObjectWrapper<>(null);
    }
}
