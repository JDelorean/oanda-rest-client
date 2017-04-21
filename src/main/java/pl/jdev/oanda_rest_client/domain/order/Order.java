package pl.jdev.oanda_rest_client.domain.order;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.text.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.jdev.oanda_rest_client.domain.instrument.CurrencyPair;

public class Order {

	private final static Logger logger = LoggerFactory.getLogger(Order.class);

	private final String id;
	private final String createTimestamp;

	private long openPrice;
	private String openTimestamp;

	private long closePrice;
	private String closeTimestamp;

	// private final long slippage;

	private CurrencyPair currencyPair;

	private int lotSize;

	private Position position;

	private Status status;

	private enum Status {
		PENDING("P"), OPEN("O"), CLOSED("C");

		private final String statusCode;

		private Status(String statusCode) {
			this.statusCode = statusCode;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public static Status setStatus(String statusCode) {
			switch (statusCode) {
			case "P":
				return PENDING;
			case "O":
				return OPEN;
			case "C":
				return CLOSED;
			}
			throw new IllegalStateException("Invalid Position code: [" + statusCode + "].");
		}
	}

	public Order(String currencyPair, String positionCode) {
		this.createTimestamp = new SimpleDateFormat("YYYY-MM-DDTHH:MM:SS.nnnnnnnnnZ").format(new Date());
		this.id = "O" + currencyPair + +createTimestamp;
		this.status = Status.setStatus("P");
		logger.debug("Created new order [" + id + "].");
	}

	public void open() {
		logger.debug("Opening order [" + id + "]...");
		// this.openPrice =
		logger.debug("Order [" + id + "] opened successfully.");
	}

	public long getOpenPrice() {
		return openPrice;
	}

	public long getClosePrice() {
		return closePrice;
	}

	public void close() {
		logger.debug("Closing order [" + id + "]...");
		// this.closePrice =
		logger.debug("Order [" + id + "] closed successfully.");
	}

	private long calculateSlippage() {
		logger.debug("Calculating slippage for order [" + id + "]...");
		// long result =
		logger.debug("Order [" + id + "] slippage is [" + null + "].");
		return 1;
	}

	public long getSlippage() {
		return 1;
	}

	public void setLotSize(int size) {
		lotSize = size;
	}

	public int getLotSize() {
		return lotSize;
	}

	public Status getStatus() {
		return status;
	}

}
