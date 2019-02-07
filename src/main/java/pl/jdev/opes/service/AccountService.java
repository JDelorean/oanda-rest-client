package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdev.opes.db.dto.AccountDto;
import pl.jdev.opes.db.dto.mapper.AccountDetailsMapper;
import pl.jdev.opes.db.dto.mapper.AccountMapper;
import pl.jdev.opes.db.repo.AccountRepository;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.domain.broker.BrokerName;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.IntegrationClient;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService extends TaggableEntityService<AccountDto, UUID> {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private IntegrationClient integrationClient;
    @Autowired
    private TagService tagService;
    @Autowired
    private AccountMapper mapper;
    @Autowired
    private AccountDetailsMapper detailsMapper;

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

    public Account addAccount(Account account) {
        AccountDto accountDto = mapper.convertToDto(account);
        return mapper.convertToEntity(accountRepository.save(accountDto));
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
        } else {
            account = new Account(UUID.randomUUID(), extId);
            account.setBroker(broker);
            account = addAccount(account);
            Account accountUpdate = getAccountDetailsFromExternal(account).orElseThrow(NotFoundException::new);
            accountUpdate.setId(account.getId());
            account = addAccount(accountUpdate);
        }
        accountRepository.setIsSyncedFlag(account.getId());
        return getAccount(account.getId());
    }

    @Transactional
    public void unsyncAccount(UUID id) throws NotFoundException {
        Optional<Boolean> exists = Optional.of(accountRepository.existsById(id));
        exists.orElseThrow(NotFoundException::new);
        accountRepository.unsetIsSyncedFlag(id);
    }

    private Optional<Account> getAccountDetailsFromExternal(Account account) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setDataType("account");
//        headers.setSource(broker.toString());
//        headers.setContentType(MediaType.APPLICATION_JSON);
        Message msg = MessageBuilder.withPayload(account)
                .setHeader(HttpHeaders.DATA_TYPE, "account")
                .setHeader(HttpHeaders.SOURCE, account.getBroker())
                .build();
        account = (Account) integrationClient.requestData(
                msg,
                Account.class)
                .getBody();
        return Optional.ofNullable(account);
    }
}