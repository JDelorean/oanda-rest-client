package pl.jdev.oanda_rest_client.domain.order;

import pl.jdev.oanda_rest_client.domain.ClientExtensions;
import pl.jdev.oanda_rest_client.domain.order.Order.OrderPositionFill;
import pl.jdev.oanda_rest_client.domain.order.Order.OrderType;
import pl.jdev.oanda_rest_client.domain.order.Order.TimeInForce;

public class OrderRequest {

    private OrderType type;
    private String instrument;
    private String units;
    private TimeInForce timeInForce;
    private String priceBound;
    private OrderPositionFill positionFill;
    private ClientExtensions clientExtensions;
    private FillingOrderDetails takeProfitOnFill;
    private FillingOrderDetails stopLossOnFill;
    private FillingOrderDetails trailingStopLossOnFill;
    private ClientExtensions tradeClientExtensions;

}