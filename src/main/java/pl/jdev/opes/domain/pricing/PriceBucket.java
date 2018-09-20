package pl.jdev.opes.domain.pricing;

import lombok.Data;

@Data
public class PriceBucket {
    private String price;
    private Integer liquidity;
}
