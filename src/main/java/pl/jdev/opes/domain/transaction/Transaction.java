package pl.jdev.opes.domain.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import pl.jdev.opes.domain.AbstractEntity;

@Data
@Builder
@TypeAlias("transaction")
public class Transaction extends AbstractEntity {
    @JsonProperty("id")
    private String transactionId;
    private String time;
    private int userID;
    private String accountID;
    private String batchID;
    private String requestID;
    private TransactionType type;
}
