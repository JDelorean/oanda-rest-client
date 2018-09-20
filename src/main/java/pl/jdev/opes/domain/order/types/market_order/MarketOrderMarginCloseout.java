package pl.jdev.opes.domain.order.types.market_order;

import lombok.Builder;
import lombok.Data;

@Data
public class MarketOrderMarginCloseout {

    public enum MarketOrderMarginCloseoutReason {
        MARGIN_CHECK_VIOLATION, REGULATORY_MARGIN_CALL_VIOLATION
    }

    private MarketOrderMarginCloseoutReason reason;

    @Builder(builderMethodName = "withReason")
    public MarketOrderMarginCloseout(MarketOrderMarginCloseoutReason reason) {
        this.reason = reason;
    }
}
