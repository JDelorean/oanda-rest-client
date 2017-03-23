package pl.jdev.oanda_rest_client.domain.instrument;

import pl.jdev.oanda_rest_client.json.Mappable;

public class CurrencyPair implements Mappable {

	private Currency baseCurrency;
	private Currency quoteCurrency;

	public CurrencyPair(String baseCode, String quoteCode) {
		this.baseCurrency = new Currency(baseCode);
		this.quoteCurrency = new Currency(quoteCode);
	}

	public Currency getBaseCurrency() {
		return baseCurrency;
	}

	public Currency getQuoteCurrency() {
		return quoteCurrency;
	}

	@Override
	public String toString() {
		return baseCurrency.toString().concat("_").concat(quoteCurrency.toString());
	}

}
