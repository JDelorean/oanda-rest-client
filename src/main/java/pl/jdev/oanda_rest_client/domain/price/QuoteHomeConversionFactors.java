package pl.jdev.oanda_rest_client.domain.price;

import lombok.Data;

@Data
public class QuoteHomeConversionFactors {
    private Float positiveUnits;
    private Float negativeUnits;
}
