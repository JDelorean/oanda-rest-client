package pl.jdev.oanda_rest_client.rest.json.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.jdev.oanda_rest_client.domain.order.Order;

@Data
@AllArgsConstructor(staticName = "payloadOf", onConstructor = @__({@JsonCreator}))
public class JsonOrderWrapper {
    private Order order;
}
