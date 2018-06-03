package pl.jdev.oanda_rest_client.domain.position;

import lombok.Data;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;

import java.io.Serializable;

@Data
public class Position implements Serializable {

	private Instrument instrument;
	private Double pl;
	private Double unrealizedPL;
	private Double resettablePL;
	private Double commission;
	private PositionSide longPosition;
	private PositionSide shortPosition;
}
