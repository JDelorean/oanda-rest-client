package pl.jdev.oanda_rest_client.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import pl.jdev.oanda_rest_client.comm.rest.json.Mappable;
import pl.jdev.oanda_rest_client.comm.rest.json.annotation.JSONObjectReference;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;

@Entity
@Table(name = "POS")
@JSONObjectReference("position")
public class Position implements Mappable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INSTR")
	@JSONObjectReference("instrument")
	private final Instrument instrument;

	@Column(name = "PL")
	@JSONObjectReference("pl")
	private Float profitLoss;

	@Column(name = "RESET_PL")
	@JSONObjectReference("resettablePL")
	private Float resettableProfitLoss;

	@Column(name = "UNREAL_PL")
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
