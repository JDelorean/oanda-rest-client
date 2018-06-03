package pl.jdev.oanda_rest_client.domain.price;

import lombok.Data;

@Data
public class PriceBucket {
    private PriceValue price;
    private Integer liquidity;
}
