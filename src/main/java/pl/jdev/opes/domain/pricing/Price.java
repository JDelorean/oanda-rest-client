package pl.jdev.opes.domain.pricing;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import pl.jdev.opes.domain.AbstractEntity;

import java.util.Collection;

@Data
@TypeAlias("price")
public class Price extends AbstractEntity {

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
