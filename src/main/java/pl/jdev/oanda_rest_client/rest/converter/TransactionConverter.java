package pl.jdev.oanda_rest_client.rest.converter;

import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.domain.transaction.Transaction;
import pl.jdev.oanda_rest_client.domain.transaction.TransactionDTO;

@Component
public class TransactionConverter implements EntityDTOConverter<Transaction, TransactionDTO> {
    @Override
    public Transaction createFrom(TransactionDTO dto) {
        return null;
    }

    @Override
    public TransactionDTO createFrom(Transaction entity) {
        return null;
    }

    @Override
    public Transaction updateEntity(Transaction entity, TransactionDTO dto) {
        return null;
    }
}
