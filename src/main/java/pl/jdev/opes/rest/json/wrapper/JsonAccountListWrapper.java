package pl.jdev.opes.rest.json.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.jdev.opes.domain.account.Account;

import java.util.Collection;

@Data
@AllArgsConstructor(staticName = "payloadOf", onConstructor = @__({@JsonCreator}))
public class JsonAccountListWrapper {
    private Collection<Account> accounts;
}
