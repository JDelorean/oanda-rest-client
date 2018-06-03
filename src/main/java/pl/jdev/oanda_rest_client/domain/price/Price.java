package pl.jdev.oanda_rest_client.domain.price;

import lombok.Data;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Price implements Serializable {
    @Deprecated
    enum PriceStatus {
        TRADEABLE, NON_TRADABLE, INVALID;
    }

    private String price;
    private Instrument instrument;
    private Date time;
    @Deprecated
    private PriceStatus status;
    private Boolean tradeable;
    private List<PriceBucket> bids;
    private List<PriceBucket> asks;
    private PriceValue closeoutAsk;
    @Deprecated
    private QuoteHomeConversionFactors quoteHomeConversionFactors;
    @Deprecated
    private UnitsAvailable unitsAvailable;
}
