package pl.jdev.oanda_rest_client.domain.pricing;

import lombok.Data;

@Data
public class UnitsAvailable {

    @Data
    class UnitsAvailableDetails {
        private double _long;
        private double _short;
    }

    private UnitsAvailableDetails _default;
    private UnitsAvailableDetails reduceFirst;
    private UnitsAvailableDetails reduceOnly;
    private UnitsAvailableDetails openOnly;
}
