package pl.jdev.oanda_rest_client.domain;

public class Currency {

	private CurrencyEnum currencyEnum;

	enum CurrencyEnum {
		EUR, USD, GBP, CHF;
	}

	public Currency(String currencyCode) {
		currencyEnum = CurrencyEnum.valueOf(currencyCode.toUpperCase());
	}

	public CurrencyEnum getCurrencyEnum() {
		return currencyEnum;
	}

	@Override
	public String toString() {
		return currencyEnum.name();
	}

}
