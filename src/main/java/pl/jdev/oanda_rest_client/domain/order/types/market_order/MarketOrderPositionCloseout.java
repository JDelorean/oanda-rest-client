package pl.jdev.oanda_rest_client.domain.order.types.market_order;

import lombok.Builder;
import lombok.Data;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;

@Data
@Builder
public class MarketOrderPositionCloseout {
    private String instrument;
    private String units;
}
