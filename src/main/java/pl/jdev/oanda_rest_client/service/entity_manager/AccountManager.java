package pl.jdev.oanda_rest_client.service.entity_manager;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.repo.AccountRepository;
import pl.jdev.oanda_rest_client.service.IEntityComparator;
import pl.jdev.oanda_rest_client.service.IManagerService;
import pl.jdev.oanda_rest_client.service.oanda_service.account.OandaAccountService;

import java.util.Collection;
import java.util.Map;

import static java.lang.String.format;

@Service
@Log
public class AccountManager implements IManagerService<Account> {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private OandaAccountService oanda;
    @Autowired
    private IEntityComparator<Account> comparator;

    @Override
    public Collection<Account> getAll() {
        Collection<Account> listOfOandaAccounts = oanda.getAllAccounts();
        Collection<Account> listOfLocalAccounts = repository.findAll();
        return comparator.compareAll(listOfOandaAccounts, listOfLocalAccounts) ? listOfLocalAccounts : listOfOandaAccounts;
    }

    @Override
    public Collection<Account> getAll(Map.Entry<String, String>... parameters) {
        return null;
    }

    @SneakyThrows
    @Override
    public Account get(ObjectId id) {
        Account localAccount = repository.findByDocumentId(id);
        Account oandaAccount = oanda.getAccount(localAccount.getAccountId());
        return comparator.compare(oandaAccount, localAccount) ? localAccount : oandaAccount;
    }

    @Override
    public Account get(String id) {
        log.info(format("Fetching account with UUID {}.", id));
        Account account = oanda.getAccount(id);
        return account;
    }

    @Override
    public void create(Account object) {
        log.info(format("Creating account {}.", object.toString()));
        repository.insert(object);
        log.info(format("Created account {}.", object.toString()));
    }

    @Override
    public void create(String object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(String object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(Account object) {

    }

}
