package pl.jdev.opes.rest.json.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.jdev.opes.domain.instrument.Candlestick;

import java.util.Collection;

@Data
@AllArgsConstructor(staticName = "payloadOf", onConstructor = @__({@JsonCreator}))
public class JsonCandlestickListWrapper {
    private Collection<Candlestick> candles;
}
