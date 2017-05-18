package pl.jdev.oanda_rest_client.rest.request.impl;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.jdev.oanda_rest_client.domain.AppDateTime;
import pl.jdev.oanda_rest_client.domain.candle.CandlestickGranularity;
import pl.jdev.oanda_rest_client.domain.candle.CandlestickPrice;

public class InstrumentRequest extends RequestImpl {

	private final static Logger logger = LoggerFactory.getLogger(InstrumentRequest.class);

	/**
	 * Fetch candlestick data for an instrument with specified <b>count</b> of
	 * candles.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getCandlesWithCount(String instrument, CandlestickPrice price, CandlestickGranularity granularity, int count)
			throws URISyntaxException {
		logger.info("Requesting all Accounts for Token.");
		Map<String, String> values = new HashMap<String, String>();
		values.put("instrument", instrument);
		values.put("price", price.name());
		values.put("granularity", granularity.name());
		values.put("count", String.valueOf(count));
		return super.getRequestFactory()
				.newGETRequest("/instruments/${instrument}/candles?count=${count}&price=${price}&granularity=${granularity}", values);
	}

	/**
	 * Fetch candlestick data for an instrument with specified <b>from</b> and
	 * <b>to</b> time frame.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getCandlesWithTimeframe(String instrument, CandlestickPrice price, CandlestickGranularity granularity,
			AppDateTime from, AppDateTime to) throws URISyntaxException {
		logger.info("Requesting all Accounts for Token.");
		Map<String, String> values = new HashMap<String, String>();
		values.put("instrument", instrument);
		values.put("price", price.name());
		values.put("granularity", granularity.name());
		values.put("from", from.toString());
		values.put("to", to.toString());
		return super.getRequestFactory()
				.newGETRequest("/instruments/${instrument}/candles?price=${price}&from=${from}&granularity=${granularity}", values);
	}

}
