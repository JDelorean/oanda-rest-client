package pl.jdev.oanda_rest_client.domain.transaction;

import pl.jdev.oanda_rest_client.domain.AbstractEntity;

import java.io.Serializable;
import java.util.Date;

public class Transaction extends AbstractEntity implements Serializable {
    private String transactionId;
    private Date time;
    private Integer userID;
    private String accountID;
    private String batchID;
    private String requestID;
    private TransactionType type;
}
