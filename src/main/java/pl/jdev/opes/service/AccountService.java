package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdev.opes.db.dto.AccountDto;
import pl.jdev.opes.db.dto.mapper.AccountMapper;
import pl.jdev.opes.db.repo.AccountRepository;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.domain.broker.BrokerName;
import pl.jdev.opes_commons.rest.IntegrationClient;

import javax.persistence.EntityExistsException;
import java.util.*;
import java.util.stream.Collectors;

import static pl.jdev.opes_commons.rest.HttpHeaders.*;
import static pl.jdev.opes_commons.rest.message.event.EventType.ACCOUNT_UPDATED;
import static pl.jdev.opes_commons.rest.message.request.RequestType.ACCOUNT_DETAILS;

@Service
public class AccountService extends TaggableEntityService<AccountDto, UUID> {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private IntegrationClient integrationClient;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TradeService tradeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private AccountMapper mapper;


    public Set<Account> getAllAccounts() {
        List<AccountDto> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(mapper::convertToEntity)
                .collect(Collectors.toSet());
    }

    public Set<Account> getSyncedAccounts() {
        List<AccountDto> accounts = accountRepository.findByIsSynced(true);
        return accounts.stream()
                .map(mapper::convertToEntity)
                .collect(Collectors.toSet());
    }

    public Account getAccount(UUID id) throws NotFoundException {
        AccountDto dto = accountRepository.findById(id).orElseThrow(NotFoundException::new);
        return mapper.convertToEntity(dto);
    }

    public Account getAccount(String extId) throws NotFoundException {
        AccountDto dto = accountRepository.findByExtId(extId).orElseThrow(NotFoundException::new);
        return mapper.convertToEntity(dto);
    }

    public boolean exists(UUID accountId) {
        return accountRepository.existsById(accountId);
    }

    private Account addAccount(Account account) {
        AccountDto accountDto = mapper.convertToDto(account);
        return mapper.convertToEntity(accountRepository.save(accountDto));
    }

    private Account updateAccount(Account update) throws NotFoundException {
        AccountDto dto = accountRepository.findByExtId(update.getExtId()).orElseThrow(NotFoundException::new);
        update.setId(dto.getId());
        return mapper.convertToEntity(accountRepository.save(mapper.convertToDto(update)));
    }

    public void deleteAccount(UUID id) throws NotFoundException {
        Optional<Boolean> exists = Optional.of(accountRepository.existsById(id));
        exists.orElseThrow(NotFoundException::new);
        accountRepository.deleteById(id);
    }

    @Transactional
    public Account syncExtAccount(String extId, BrokerName broker) throws NotFoundException, EntityExistsException {
        Optional<AccountDto> accountDtoOptional = accountRepository.findByExtId(extId);
        Account account = null;
        if (accountDtoOptional.isPresent()) {
            AccountDto accountDto = accountDtoOptional.get();
            if (accountDto.isSynced())
                throw new EntityExistsException(String.format("Account with external ID %s already synced!", accountDto.getExtId()));
            account = mapper.convertToEntity(accountDto);
        } else {
            account = new Account(UUID.randomUUID(), extId);
            account.setBroker(broker);
            account = addAccount(account);
            Account accountUpdate = getAccountDetailsFromExternal(account).orElseThrow(NotFoundException::new);
            accountUpdate.setId(account.getId());
            account = addAccount(accountUpdate);
        }
        orderService.syncOrdersForAccount(account);
        tradeService.syncTradesForAccount(account);
        accountRepository.setIsSyncedFlag(account.getId());
        return getAccount(account.getId());
    }

    @Transactional
    public void unsyncAccount(UUID id) throws NotFoundException {
        Account account = getAccount(id);
        accountRepository.unsetIsSyncedFlag(id);
        Message msg = MessageBuilder.withPayload(account)
                .setHeader(REQUEST_TYPE, ACCOUNT_DETAILS)
                .setHeader(SOURCE, account.getBroker())
                .build();
        account = (Account) integrationClient.request(
                msg,
                Account.class)
                .getBody();
    }

    private Optional<Account> getAccountDetailsFromExternal(Account account) {
        Message msg = MessageBuilder.withPayload(account)
                .setHeader(REQUEST_TYPE, ACCOUNT_DETAILS)
                .setHeader(SOURCE, account.getBroker())
                .build();
        account = (Account) integrationClient.request(
                msg,
                Account.class)
                .getBody();
        return Optional.ofNullable(account);
    }

    @Scheduled(fixedRateString = "${opes.poll.account}")
    void pollAccount() {
        Map<UUID, Account> accounts = accountRepository
                .findByIsSynced(true)
                .stream()
                .map(mapper::convertToEntity)
                .collect(Collectors.toMap(Account::getId, account -> account));
        accounts.values()
                .parallelStream()
                .map(this::getAccountDetailsFromExternal)
                .map(Optional::get)
                .map(account -> {
                    UUID accountId = accountRepository
                            .findByExtId(account.getExtId())
                            .get()
                            .getId();
                    account.setId(accountId);
                    return account;
                })
                .filter(account -> !accounts.get(account.getId()).equals(account))
                .map(this::updateAccount)
                .map(account -> MessageBuilder
                        .withPayload(account)
                        .setHeader(EVENT_TYPE, ACCOUNT_UPDATED)
                        .build())
                .forEach(integrationClient::post);
    }

}