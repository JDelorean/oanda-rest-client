package pl.jdev.opes.rest.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.jdev.opes_commons.domain.broker.BrokerName;

import java.util.Currency;

@Getter
@RequiredArgsConstructor
public class OrderRequestModel {
    private final BrokerName broker;
    private final Double units;
    private final Currency currency;

}
