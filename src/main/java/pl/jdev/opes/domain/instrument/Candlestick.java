package pl.jdev.opes.domain.instrument;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import pl.jdev.opes.domain.AbstractEntity;

@Data
@Builder
@TypeAlias("candle")
public class Candlestick extends AbstractEntity {

    @Data
    private class CandlestickData {
        private Double o;
        private Double h;
        private Double l;
        private Double c;
    }

    private String time;
    private CandlestickData bid;
    private CandlestickData ask;
    private CandlestickData mid;
    private int volume;
    private boolean complete;
}
