package pl.jdev.oanda_rest_client.domain.order.types.market_order;

import lombok.Data;
import pl.jdev.oanda_rest_client.domain.ClientExtensions;
import pl.jdev.oanda_rest_client.domain.order.*;
import pl.jdev.oanda_rest_client.rest.validation.SupportedOrderType;
import pl.jdev.oanda_rest_client.rest.validation.SupportedTimeInForce;

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
