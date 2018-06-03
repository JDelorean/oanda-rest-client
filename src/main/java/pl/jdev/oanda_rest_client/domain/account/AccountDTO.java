package pl.jdev.oanda_rest_client.domain.account;


import lombok.Data;
import pl.jdev.oanda_rest_client.domain.AbstractDto;
import pl.jdev.oanda_rest_client.domain.order.OrderDto;
import pl.jdev.oanda_rest_client.domain.position.PositionDto;
import pl.jdev.oanda_rest_client.domain.trade.TradeDto;
import pl.jdev.oanda_rest_client.rest.validation.SupportedCurrency;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

@Data
public class AccountDTO extends AbstractDto<Account> {

    @Null
    private String accountId;

    @NotNull
    private String alias;

    @NotNull
    @SupportedCurrency
    private String currency;

    @Null
    private Double balance;

    // @NotNull
    private String createdByUserID;

    @Null
    private Double pl;
    private Double resettablePL;
    private Date resettablePLTime;
    private Double commission;
    private Double marginRate;
    private Date marginCallEnterTime;
    private Integer marginCallExtensionCount;
    private Date lastMarginCallExtensionTime;
    private Integer openTradeCount;
    private Integer openPositionCount;
    private Integer pendingOrderCount;
    private Boolean hedgingEnabled;
    private Double unrealizedPL;
    private Double nav;
    private Double marginUsed;
    private Double marginAvailable;
    private Double positionValue;
    private Double marginCloseoutNAV;
    private Double marginCloseoutMarginUsed;
    private Double marginCloseoutPercent;
    @Null
    private Double lastTransactionID;
    //    @Transient
    @Null
    private List<TradeDto> trades;
    //    @Transient
    @Null
    private List<PositionDto> positions;
    //    @Transient
    @Null
    private List<OrderDto> orders;
}
