package pl.jdev.oanda_rest_client.rest.json.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

@Data
@AllArgsConstructor(staticName = "payloadOf", onConstructor = @__({@JsonCreator}))
public class JsonTradeWrapper {
    private Trade trade;
}
