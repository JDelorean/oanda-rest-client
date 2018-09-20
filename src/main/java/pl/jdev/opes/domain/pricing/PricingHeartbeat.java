package pl.jdev.opes.domain.pricing;

import lombok.Data;

@Data
public class PricingHeartbeat {
    private String type;
    private String time;
}
