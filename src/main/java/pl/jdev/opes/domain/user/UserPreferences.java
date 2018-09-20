package pl.jdev.opes.domain.user;

import lombok.Data;

import java.util.Collection;

@Data
public class UserPreferences {

    private Collection<String> currencyPairs;
}
