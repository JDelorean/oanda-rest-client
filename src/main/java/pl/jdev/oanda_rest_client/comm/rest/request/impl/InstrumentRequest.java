package pl.jdev.oanda_rest_client.comm.rest.request.impl;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.jdev.oanda_rest_client.domain.AppDateTime;
import pl.jdev.oanda_rest_client.domain.candle.CandlestickGranularity;
import pl.jdev.oanda_rest_client.domain.candle.CandlestickPriceType;

public class InstrumentRequest extends RequestImpl {

	private final static Logger LOGGER = LogManager.getLogger(InstrumentRequest.class);

	/**
	 * Fetch candlestick data for an instrument with specified <b>count</b> of
	 * candles.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getCandlesWithCount(String instrument, CandlestickPriceType priceType, CandlestickGranularity granularity, int count)
			throws URISyntaxException {
		LOGGER.debug("Requesting Candles for {}, price type {}, granularity {}, count {}...", instrument, priceType, granularity, count);

		Map<String, String> values = new HashMap<String, String>();
		values.put("instrument", instrument);
		values.put("price", priceType.name());
		values.put("granularity", granularity.name());
		values.put("count", String.valueOf(count));
		HttpRequest request = super.getRequestFactory()
				.newGETRequest("/instruments/${instrument}/candles?count=${count}&price=${price}&granularity=${granularity}", values);

		LOGGER.debug("Returning request {}.", request);
		return request;
	}

	/**
	 * Fetch candlestick data for an instrument with specified <b>from</b> and
	 * <b>to</b> time frame.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getCandlesWithTimeframe(String instrument, CandlestickPriceType priceType, CandlestickGranularity granularity,
			AppDateTime from, AppDateTime to) throws URISyntaxException {
		LOGGER.debug("Requesting Candles for {}, price type {}, granularity {}, from {} to {}...", instrument, priceType, granularity, from,
				to);

		Map<String, String> values = new HashMap<String, String>();
		values.put("instrument", instrument);
		values.put("price", priceType.name());
		values.put("granularity", granularity.name());
		values.put("from", from.toString());
		values.put("to", to.toString());
		HttpRequest request = super.getRequestFactory()
				.newGETRequest("/instruments/${instrument}/candles?price=${price}&from=${from}&granularity=${granularity}", values);

		LOGGER.debug("Returning request {}.", request);
		return request;
	}

}
