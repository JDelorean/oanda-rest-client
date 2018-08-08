package pl.jdev.oanda_rest_client.rest.controller;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.repo.AccountRepository;
import pl.jdev.oanda_rest_client.rest.exception.AccountNotFoundException;
import pl.jdev.oanda_rest_client.service.oanda_service.account.OandaAccountService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@Log
public class AccountController extends AbstractRestController<Account> {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    OandaAccountService oandaAccountService;

    @GetMapping
    @ResponseBody
    public Map<String, Object> getAllAccounts() {
        return Map.of("accounts", oandaAccountService.getAllAccounts());
    }

//    @GetMapping(value = "/{_id}", params = "idType=document")
//    @ResponseBody
//    public Map<String, Object> getObject(@Valid @PathVariable(required = true) ObjectId _id) {
//        Account object = this.accountRepository.findByDocumentId(_id);
//        if (object == null) {
//            throw new AccountNotFoundException(_id.toString());
//        }
//        return Map.of("object", object);
//    }

    //TODO: remove sneakyhrows
    @GetMapping(value = "/{accountId}")
    @ResponseBody
    @SneakyThrows
    public Map<String, Object> getAccountFromOanda(@Valid @PathVariable(required = true) final String accountId) {
        Account account = this.oandaAccountService.getAccount(accountId);
        if (account == null) {
            throw new AccountNotFoundException(accountId);
        }
        return Map.of("account", account);
    }
}
