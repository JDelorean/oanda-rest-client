package pl.jdev.opes.helper;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.rest.message.event.EventType;

import static pl.jdev.opes_commons.rest.HttpHeaders.EVENT_TYPE;

@Component
public class EventMessageFactory {
    public static Message accountUnsyncedMsg(Account account) {
        return MessageBuilder.withPayload(account)
                .setHeader(EVENT_TYPE, EventType.ACCOUNT_UNSYNCED)
                .build();
    }
}
