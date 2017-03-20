package pl.jdev.oanda_rest_client.domain.instrument;

public class Currency {

	private InstrumentType INSTRUMENT = InstrumentType.CURRENCY;
	private CurrencyEnum currencyEnum;

	enum CurrencyEnum {
		EUR, USD, GBP, CHF;
	}

	public Currency(String currencyCode) {
		currencyEnum = CurrencyEnum.valueOf(currencyCode.toUpperCase());
	}

	public InstrumentType getInstrumentType() {
		return INSTRUMENT;
	}

	public CurrencyEnum getCurrencyEnum() {
		return currencyEnum;
	}

	@Override
	public String toString() {
		return currencyEnum.name();
	}

}
