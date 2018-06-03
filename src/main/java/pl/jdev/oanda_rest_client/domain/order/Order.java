package pl.jdev.oanda_rest_client.domain.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;
import pl.jdev.oanda_rest_client.domain.ClientExtensions;

@Data
public class Order implements Serializable {

	enum OrderState {
		PENDING, FILLED, TRIGGERED, CANCELLED;
	}

	public enum OrderType {
		MARKET, LIMIT, STOP, MARKET_IF_TOUCHED, TAKE_PROFIT, STOP_LOSS, TRAILING_STOP_LOSS;
	}

	public enum TimeInForce {
		GTC, GTD, GFD, FOK, IOC;
	}

	enum OrderPositionFill {
		OPEN_ONLY, REDUCE_FIRST, REDUCE_ONLY, DEFAULT;
	}

	@Id
	private UUID id;
	private Date createTime;
	private OrderState state;
	private ClientExtensions clientExtensions;
	private OrderType type;
	private String instrument;
	private String units;
	private TimeInForce timeInForce;
	private String priceBound;
	private OrderPositionFill positionFill;
	// private MarketOrderTradeClose tradeClose;
	// private MarketOrderPositionCloseout longPositionCloseout;
	// private MarketOrderPositionCloseout shortPositionCloseout;
	// private MarketOrderMarginCloseout marginCloseout;
	// private MarketOrderDelayedTradeClose delayedTradeClose;
	private FillingOrderDetails takeProfitOnFill;
	private FillingOrderDetails stopLossOnFill;
	private FillingOrderDetails trailingStopLossOnFill;
	private ClientExtensions tradeClientExtensions;
	private String fillingTransactionID;
	private Date filledTime;
	private String tradeOpenedID;
	private String tradeReducedID;
	private List<String> tradeClosedIDs;
	private String cancellingTransactionID;
	private Date cancelledTime;
}