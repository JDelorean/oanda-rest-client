package pl.jdev.oanda_rest_client.domain.instrument;

import pl.jdev.oanda_rest_client.domain.Currency;
import pl.jdev.oanda_rest_client.json.annotation.JSONArrayReference;
import pl.jdev.oanda_rest_client.json.annotation.JSONObjectReference;

@JSONObjectReference("instrument")
@JSONArrayReference(value = "instruments", classReference = CurrencyPair.class)
public class CurrencyPair extends Instrument {

	private Currency baseCurrency;
	private Currency quoteCurrency;
	@JSONObjectReference("name")
	private String pair;

	public CurrencyPair() {
		super(InstrumentType.CURRENCY);
	}

	public CurrencyPair(String pair) {
		super(InstrumentType.CURRENCY);
		this.pair = pair;
		this.baseCurrency = new Currency(pair.substring(0, 3));
		this.quoteCurrency = new Currency(pair.substring(4));
	}

	public CurrencyPair(String baseCode, String quoteCode) {
		super(InstrumentType.CURRENCY);
		this.baseCurrency = new Currency(baseCode);
		this.quoteCurrency = new Currency(quoteCode);
		this.pair = baseCurrency.toString().concat("_").concat(quoteCurrency.toString());
	}

	public Currency getBaseCurrency() {
		return baseCurrency;
	}

	public Currency getQuoteCurrency() {
		return quoteCurrency;
	}

	public String getPair() {
		return pair;
	}

}
