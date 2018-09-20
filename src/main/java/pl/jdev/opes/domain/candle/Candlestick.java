package pl.jdev.opes.domain.candle;

import lombok.Data;

import java.util.Date;

@Data
public class Candlestick {

    @Data
    private class CandlestickData {
        private Double o;
        private Double h;
        private Double l;
        private Double c;
    }

    private Date time;
    private CandlestickData bid;
    private CandlestickData ask;
    private CandlestickData mid;
    private Integer volume;
    private Boolean complete;
}
