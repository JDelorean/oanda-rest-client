package pl.jdev.oanda_rest_client.domain.candle;

import java.util.Date;

import lombok.Data;

@Data
public class Candlestick {

	@Data
	class CandlestickData {
		private Double o;
		private Double h;
		private Double l;
		private Double c;
	}

	private Date time;
	private CandlestickData bid;
	private CandlestickData ask;
	private CandlestickData mid;
	private Integer volume;
	private Boolean complete;
}
