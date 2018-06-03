package pl.jdev.oanda_rest_client.domain.price;

import lombok.Data;

@Data
public class UnitsAvailable {

    @Data
    class UnitsAvailableDetails {
        private Float _long;
        private Float _short;
    }

    private UnitsAvailableDetails _default;
    private UnitsAvailableDetails reduceFirst;
    private UnitsAvailableDetails reduceOnly;
    private UnitsAvailableDetails openOnly;
}
