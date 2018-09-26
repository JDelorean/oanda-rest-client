package pl.jdev.opes.domain.transaction;


import lombok.Builder;
import lombok.Data;
import pl.jdev.opes.domain.Currency;

@Data
@Builder
public class CreateTransaction {
    private Integer divisionID;
    private Integer siteID;
    private Integer accountUserID;
    private Integer accountNumber;
    private Currency homeCurrency;
}
