package pl.jdev.oanda_rest_client.domain.position;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;

@Data
@TypeAlias("position")
public class Position extends AbstractEntity {
    private Instrument instrument;
    private Double pl;
    private Double unrealizedPL;
    private Double resettablePL;
    private Double commission;
    private PositionSide longPosition;
    private PositionSide shortPosition;
}
