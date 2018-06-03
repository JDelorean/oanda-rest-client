package pl.jdev.oanda_rest_client.domain.account;

import lombok.Data;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;
import pl.jdev.oanda_rest_client.domain.order.Order;
import pl.jdev.oanda_rest_client.domain.position.Position;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

import java.io.Serializable;
import java.util.Currency;
import java.util.Date;
import java.util.List;

@Data
public class Account extends AbstractEntity implements Serializable {
    private String accountId;
    private String alias;
    private Currency currency;
    private Double balance;
    private String createdByUserID;
    //    private Date createdTime;
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
    private Double lastTransactionID;
    //    @Transient
    private List<Trade> trades;
    //    @Transient
    private List<Position> positions;
    //    @Transient
    private List<Order> orders;
}