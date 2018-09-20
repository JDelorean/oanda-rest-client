package pl.jdev.opes.domain.order.types.market_order;

import lombok.Data;
import pl.jdev.opes.domain.ClientExtensions;
import pl.jdev.opes.domain.order.*;
import pl.jdev.opes.rest.validation.SupportedOrderType;
import pl.jdev.opes.rest.validation.SupportedTimeInForce;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class MarketOrderRequest extends OrderRequest {
    @SupportedOrderType
    @NotNull
    private OrderType type;
    @NotNull
    private String instrument;
    @NotNull
    @Digits(fraction = 6, integer = 6)
    private long units;
    @SupportedTimeInForce
    private TimeInForce timeInForce;
    private String priceBound;
    private OrderPositionFill positionFill;
    private ClientExtensions clientExtensions;
    @Null
    private FillingOrderDetails takeProfitOnFill;
    @Null
    private FillingOrderDetails stopLossOnFill;
    @Null
    private FillingOrderDetails trailingStopLossOnFill;
    private ClientExtensions tradeClientExtensions;
}
