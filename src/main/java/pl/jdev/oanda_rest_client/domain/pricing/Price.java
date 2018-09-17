package pl.jdev.oanda_rest_client.domain.pricing;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;

import java.util.Collection;

@Data
public class Price extends AbstractEntity {
    @Deprecated
    enum PriceStatus {
        TRADEABLE, NON_TRADABLE, INVALID;
    }

    private String price;
    @Indexed
    private String instrument;
    @Indexed
    private String time;
    @Deprecated
    private PriceStatus status;
    private boolean tradeable;
    private Collection<PriceBucket> bids;
    private Collection<PriceBucket> asks;
    private String closeoutBid;
    private String closeoutAsk;
    @Deprecated
    private QuoteHomeConversionFactors quoteHomeConversionFactors;
    @Deprecated
    private UnitsAvailable unitsAvailable;
}
