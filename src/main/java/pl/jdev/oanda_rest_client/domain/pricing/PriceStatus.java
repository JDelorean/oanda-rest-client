package pl.jdev.oanda_rest_client.domain.pricing;

import com.fasterxml.jackson.annotation.JsonProperty;

@Deprecated
enum PriceStatus {
    @JsonProperty("tradeable")
    TRADEABLE,
    @JsonProperty("non_tradeable")
    NON_TRADABLE,
    @JsonProperty("invalid")
    INVALID;
}