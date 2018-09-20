package pl.jdev.opes.domain.position;

import java.util.List;

import lombok.Data;

@Data
public class PositionSide {

	private Double units;
	private Double averagePrice;
	private List<String> tradeIDs;
	private Double pl;
	private Double unrealizedPL;
	private Double resettablePL;
}
