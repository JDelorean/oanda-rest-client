package pl.jdev.opes_abacus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovingAverage implements Serializable {
    private String instrument;
    private CandlestickGranularity granularity;
    private Integer periodLength;
    private String time;
    private Double value;
}
