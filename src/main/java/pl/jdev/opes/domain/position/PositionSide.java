package pl.jdev.opes.domain.position;

import lombok.Data;

import java.util.List;

@Data
class PositionSide {

    private Double units;
    private Double averagePrice;
    private List<String> tradeIDs;
    private Double pl;
    private Double unrealizedPL;
    private Double resettablePL;
}
