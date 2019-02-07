package pl.jdev.opes.rest.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.jdev.opes_commons.domain.broker.BrokerName;

@Getter
@RequiredArgsConstructor
public class AccountSyncRequestModel {
    private final String extId;
    private final BrokerName broker;
}
