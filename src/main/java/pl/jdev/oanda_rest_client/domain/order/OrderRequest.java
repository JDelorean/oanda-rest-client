package pl.jdev.oanda_rest_client.domain.order;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import pl.jdev.oanda_rest_client.domain.ClientExtensions;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;
import pl.jdev.oanda_rest_client.domain.order.Order.OrderPositionFill;
import pl.jdev.oanda_rest_client.domain.order.Order.OrderType;
import pl.jdev.oanda_rest_client.domain.order.Order.TimeInForce;
import pl.jdev.oanda_rest_client.rest.validation.SupportedOrderType;
import pl.jdev.oanda_rest_client.rest.validation.SupportedTimeInForce;

public class OrderRequest {

	@SupportedOrderType
	private OrderType type;
	@NotNull
	private Instrument instrument;
	@NotNull
	@Digits(fraction = 6, integer = 2)
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