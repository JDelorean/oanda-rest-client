package pl.jdev.opes.domain.position;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import pl.jdev.opes.domain.AbstractEntity;
import pl.jdev.opes.domain.instrument.Instrument;

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
