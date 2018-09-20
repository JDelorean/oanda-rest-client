package pl.jdev.opes.domain.pricing;

import lombok.Data;

@Data
class UnitsAvailable {

    @Data
    private class UnitsAvailableDetails {
        private double _long;
        private double _short;
    }

    private UnitsAvailableDetails _default;
    private UnitsAvailableDetails reduceFirst;
    private UnitsAvailableDetails reduceOnly;
    private UnitsAvailableDetails openOnly;
}
