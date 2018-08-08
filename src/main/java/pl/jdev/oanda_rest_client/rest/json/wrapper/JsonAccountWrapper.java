package pl.jdev.oanda_rest_client.rest.json.wrapper;

import lombok.Data;
import pl.jdev.oanda_rest_client.domain.account.Account;

@Data
public class JsonAccountWrapper {
    private Account account;
}
