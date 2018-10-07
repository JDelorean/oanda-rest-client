package pl.jdev.opes.domain.order.types.market_order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarketOrderPositionCloseout {
    private String instrument;
    private String units;
}
