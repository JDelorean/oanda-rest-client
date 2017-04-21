package pl.jdev.oanda_rest_client.domain.instrument;

import pl.jdev.oanda_rest_client.domain.Currency;
import pl.jdev.oanda_rest_client.json.JSONReference;

@JSONReference(value = "instruments", isArrayElement = true)
public class CurrencyPair extends Instrument {

	@JSONReference("type")
	private InstrumentType INSTRUMENT_TYPE = InstrumentType.CURRENCY;
	private Currency baseCurrency;
	private Currency quoteCurrency;
	@JSONReference("name")
	private String pair;

	public CurrencyPair() {
	}

	public CurrencyPair(String baseCode, String quoteCode) {
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

	public InstrumentType getInstrumentType() {
		return INSTRUMENT_TYPE;
	}

	public String getPair() {
		return pair;
	}

}
