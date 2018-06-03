package pl.jdev.oanda_rest_client.rest.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.service.oanda_service.account.OandaAccountService;

import java.util.Collection;

@RestController
@RequestMapping("/api/oanda_service/accounts")
@Slf4j
public class OandaAccountController {

    @Autowired
    private OandaAccountService oandaAccountService;

    @GetMapping
    public ResponseEntity<Collection<Account>> getAllAccounts() {
        return ResponseEntity.ok(oandaAccountService.getAllAccounts());
    }

    @SneakyThrows
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable(required = true) final String accountId) {
        return ResponseEntity.ok(oandaAccountService.getAccount(accountId));
    }

    @GetMapping("/{accountId}/summary")
    public ResponseEntity<Account> getAccountSummary(@PathVariable(required = true) final String accountId) {
        return ResponseEntity.ok(oandaAccountService.getAccountSummary(accountId));
    }

    @GetMapping("/{accountId}/instruments")
    public ResponseEntity<Account> getAccountInstruments(@PathVariable(required = true) final String accountId,
                                                         @RequestParam(name = "instruments", required = false) String instruments) {
        return ResponseEntity.ok(oandaAccountService.getAccountInstruments(accountId, instruments));
    }

    // @PatchMapping("/{accountId}/configuration")
    // public ResponseEntity<ClientConfigureTransaction> patchAccountConfiguration(
    // @PathVariable(required = true) String accountId, @RequestBody String body) {
    // return ResponseEntity.ok(oandaAccountService.);
    // }

}
