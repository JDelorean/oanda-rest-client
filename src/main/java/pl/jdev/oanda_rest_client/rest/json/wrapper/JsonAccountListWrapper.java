package pl.jdev.oanda_rest_client.rest.json.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.jdev.oanda_rest_client.domain.account.Account;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "payloadOf", onConstructor = @__({@JsonCreator}))
public class JsonAccountListWrapper {
    private List<Account> accounts;
}
