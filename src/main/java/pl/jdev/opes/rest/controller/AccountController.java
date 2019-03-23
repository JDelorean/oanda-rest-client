package pl.jdev.opes.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes.service.AccountService;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.rest.json.AccountViews;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@Log4j2
public class AccountController extends AbstractEntityController<Account> {
    @Autowired
    AccountService accountService;

    @GetMapping
    @ResponseBody
    public Set<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/synced")
    @ResponseBody
    public Set<Account> getSyncedAccounts() {
        return accountService.getSyncedAccounts();
    }

    @GetMapping(value = "/{accountId}")
    @ResponseBody
    public Account getAccount(@Valid @PathVariable final UUID accountId) throws NotFoundException {
        return accountService.getAccount(accountId);
    }

    @PostMapping(value = "/sync")
    @ResponseBody
    public Account syncAccount(@JsonView(AccountViews.Sync.class) @Valid @RequestBody Account syncRequest) throws NotFoundException {
        return accountService.syncExtAccount(syncRequest.getExtId(), syncRequest.getBroker());
    }


    @PostMapping(value = "/{accountId}/unsync")
    @ResponseBody
    public void unsyncAccount(@Valid @PathVariable final UUID accountId) throws NotFoundException {
        accountService.unsyncAccount(accountId);
    }

    @DeleteMapping(value = "/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@Valid @PathVariable final UUID accountId) throws NotFoundException {
        accountService.deleteAccount(accountId);
    }

    @PostMapping("/{accountId}/tag")
    public void tagAccount(@PathVariable(name = "accountId") final UUID accountId,
                           @RequestParam final List<String> tags)
            throws NotFoundException {
        tags.forEach(tag -> accountService.tagEntity(accountId, tag));
    }

    @PostMapping("/{accountId}/untag")
    public void untagAccount(@PathVariable(name = "accountId") final UUID accountId,
                             @RequestParam final List<String> tags)
            throws NotFoundException {
        tags.forEach(tag -> accountService.removeEntityTag(accountId, tag));
    }
}