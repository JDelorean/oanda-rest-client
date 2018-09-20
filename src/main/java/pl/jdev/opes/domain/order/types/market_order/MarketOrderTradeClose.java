package pl.jdev.opes.domain.order.types.market_order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarketOrderTradeClose {
    private String tradeID;
    private String clientTradeID;
    private String units;
}
