package pl.jdev.oanda_rest_client.domain.transaction;


import lombok.Builder;
import lombok.Data;
import pl.jdev.oanda_rest_client.domain.Currency;

@Data
@Builder
public class CreateTransaction {
    private Integer divisionID;
    private Integer siteID;
    private Integer accountUserID;
    private Integer accountNumber;
    private Currency homeCurrency;
}
