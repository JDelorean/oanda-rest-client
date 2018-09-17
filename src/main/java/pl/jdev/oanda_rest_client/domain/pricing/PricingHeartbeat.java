package pl.jdev.oanda_rest_client.domain.pricing;

import lombok.Data;

@Data
public class PricingHeartbeat {
    private String type;
    private String time;
}
