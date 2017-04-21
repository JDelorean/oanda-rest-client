package pl.jdev.oanda_rest_client.domain;

import pl.jdev.oanda_rest_client.domain.instrument.Instrument;
import pl.jdev.oanda_rest_client.json.JSONReference;
import pl.jdev.oanda_rest_client.json.Mappable;

@JSONReference(value = "positions", isArrayElement = true)
public class Position implements Mappable {

	@JSONReference("instrument")
	private Instrument instrument;

	@JSONReference("pl")
	private Float profitLoss;

	@JSONReference("resettablePL")
	private Float resettableProfitLoss;

	@JSONReference("unrealizedProfitLoss")
	private Float unrealizedProfitLoss;

}
