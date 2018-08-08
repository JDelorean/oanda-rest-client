package pl.jdev.oanda_rest_client.rest.json.wrapper;

import lombok.Data;
import pl.jdev.oanda_rest_client.domain.account.Account;

import java.util.List;

@Data
public class JsonAccountListWrapper {
    private List<Account> accounts;
}
