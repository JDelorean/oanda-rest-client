package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.rest.client.IntegrationClient;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.jdev.opes_commons.rest.HttpHeaders.EVENT_TYPE;
import static pl.jdev.opes_commons.rest.message.event.EventType.ACCOUNT_UPDATED;

@Service
public class AccountJob {
    @Autowired
    private AccountService accountService;
    @Autowired
    private IntegrationClient integrationClient;

    @Scheduled(fixedRateString = "${opes.poll.account}")
    protected void pollAccounts() {
        Map<UUID, Account> accounts = accountService.getSyncedAccounts()
                .stream()
                .collect(Collectors.toMap(Account::getId, account -> account));
        accounts.values()
                .parallelStream()
                .map(accountService::getAccountDetailsFromExternal)
                .map(Optional::get)
                .map(account -> {
                    UUID accountId = accountService.getAccount(account.getExtId()).getId();
                    account.setId(accountId);
                    return account;
                })
                .filter(account -> !accounts.get(account.getId()).equals(account))
                .map(accountService::updateAccount)
                .map(account -> MessageBuilder
                        .withPayload(account)
                        .setHeader(EVENT_TYPE, ACCOUNT_UPDATED)
                        .build())
                .forEach(integrationClient::post);
    }
}
