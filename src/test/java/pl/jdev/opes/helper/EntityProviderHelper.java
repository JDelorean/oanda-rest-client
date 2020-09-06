package pl.jdev.opes.helper;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import pl.jdev.opes.db.dto.mapper.AccountMapper;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.domain.broker.BrokerName;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_commons.domain.trade.Trade;

import java.util.*;

@TestConfiguration
public class EntityProviderHelper {
    @Autowired
    private static Faker faker = new Faker();
    @Autowired
    private static AccountMapper mapper;

    public static Account account() {
//        AccountDto dto = new AccountDto(UUID.randomUUID(),
//                faker.numerify("####-####"),
//                BrokerName.OANDA,
//                Currency.getInstance(faker.currency().code()),
//                faker.number().randomDouble(2, -1000000, 1000000),
//                faker.bool().bool(),
//                orders(),
//                trades(),
//                tags());
        return new Account(UUID.randomUUID(),
                faker.numerify("####-####"),
                Currency.getInstance(faker.currency().code()),
                faker.number().randomDouble(2, -1000000, 1000000),
                BrokerName.OANDA);
    }

    private static Set<Trade> trades() {
        return null;
    }

    private static Set<Order> orders() {
        return null;
    }

    public static List<Account> accountSet() {
        List<Account> accountList = new ArrayList<>();
        for (int i = 0; i <= faker.number().numberBetween(0, 20); i++) accountList.add(account());
        return accountList;
    }

    public static String tag() {
        return faker.color().name();
    }

    public static Set<String> tags() {
        Set<String> tags = new HashSet<>();
        for (int i = 0; i <= faker.number().numberBetween(0, 5); i++) tags.add(tag());
        return tags;
    }
}
