package pl.jdev.oanda_rest_client.domain.pricing;

import lombok.Data;

@Data
public class QuoteHomeConversionFactors {
    private double positiveUnits;
    private double negativeUnits;
}
