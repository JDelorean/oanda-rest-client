package pl.jdev.opes.domain.pricing;

import lombok.Data;

@Data
class PriceBucket {
    private String price;
    private Integer liquidity;
}
