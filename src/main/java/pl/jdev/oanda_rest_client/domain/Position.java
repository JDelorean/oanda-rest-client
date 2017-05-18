package pl.jdev.oanda_rest_client.domain;

import pl.jdev.oanda_rest_client.domain.instrument.Instrument;
import pl.jdev.oanda_rest_client.json.Mappable;
import pl.jdev.oanda_rest_client.json.annotation.JSONObjectReference;

@JSONObjectReference("position")
public class Position implements Mappable {

	@JSONObjectReference("instrument")
	private final Instrument instrument;

	@JSONObjectReference("pl")
	private Float profitLoss;

	@JSONObjectReference("resettablePL")
	private Float resettableProfitLoss;

	@JSONObjectReference("unrealizedProfitLoss")
	private Float unrealizedProfitLoss;

	public Position(Instrument instrument) {
		this.instrument = instrument;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public Float getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(Float profitLoss) {
		this.profitLoss = profitLoss;
	}

	public Float getResettableProfitLoss() {
		return resettableProfitLoss;
	}

	public void setResettableProfitLoss(Float resettableProfitLoss) {
		this.resettableProfitLoss = resettableProfitLoss;
	}

	public Float getUnrealizedProfitLoss() {
		return unrealizedProfitLoss;
	}

	public void setUnrealizedProfitLoss(Float unrealizedProfitLoss) {
		this.unrealizedProfitLoss = unrealizedProfitLoss;
	}

}
