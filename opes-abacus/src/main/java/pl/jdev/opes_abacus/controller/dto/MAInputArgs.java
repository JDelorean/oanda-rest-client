package pl.jdev.opes_abacus.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.jdev.opes_commons.domain.instrument.Candlestick;

@Getter
@AllArgsConstructor
@ToString
public class MAInputArgs implements InputArgs {
    private CalculationType type;
    private int periodLength;
    private Candlestick[] candles;
}
