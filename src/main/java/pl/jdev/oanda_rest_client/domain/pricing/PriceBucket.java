package pl.jdev.oanda_rest_client.domain.pricing;

import lombok.Data;

@Data
public class PriceBucket {
    private String price;
    private Integer liquidity;
}
