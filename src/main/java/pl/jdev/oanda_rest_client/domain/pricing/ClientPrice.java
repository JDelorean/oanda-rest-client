package pl.jdev.oanda_rest_client.domain.pricing;

import lombok.Data;

import java.util.Collection;

@Data
public class ClientPrice {
    private Collection<PriceBucket> bids;
    private Collection<PriceBucket> asks;
    private String closeoutBid;
    private String closeoutAsk;
    private String timestamp;
}
