package pl.jdev.oanda_rest_client.domain.transaction;


import pl.jdev.oanda_rest_client.domain.Currency;

public class CreateTransaction extends Transaction {
    private Integer dividionID;
    private Integer siteID;
    private Integer accountUserID;
    private Integer accountNumber;
    private Currency homeCurrency;
}
