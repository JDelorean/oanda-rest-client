package pl.jdev.opes.rest.controller;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.rest.json.wrapper.JsonAccountListWrapper;
import pl.jdev.opes.rest.json.wrapper.JsonAccountWrapper;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.rest.message.GenericDataRequest;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/accounts")
@Log4j2
public class AccountController extends AbstractEntityController<Account> {

    @GetMapping
    @ResponseBody
    public JsonAccountListWrapper getAllAccounts() {
        return JsonAccountListWrapper.payloadOf(
                (Collection<Account>) integrationClient.requestData(
                        new GenericDataRequest(),
                        null)
                        .getBody());
    }

    //TODO: remove sneakyhrows
    @GetMapping(value = "/{accountId}")
    @ResponseBody
    @SneakyThrows
    public JsonAccountWrapper getAccount(@Valid @PathVariable final String accountId) {
        Account account = (Account) integrationClient.requestData(
                new GenericDataRequest(),
                null
        ).getBody();
        if (account == null) throw new IOException(String.format("No Account found with ID %s!", accountId));
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