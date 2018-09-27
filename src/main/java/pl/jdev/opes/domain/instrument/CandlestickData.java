package pl.jdev.opes.domain.instrument;

import lombok.Data;

@Data
public class CandlestickData {
    private double o;
    private double h;
    private double l;
    private double c;
}
