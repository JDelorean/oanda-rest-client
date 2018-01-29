package pl.jdev.oanda_rest_client.domain.instrument;

public class Instrument {

    enum InstrumentType {
        CURRENCY, CFD, METAL;
    }

    class InstrumentCommission {
        private String instrument;
        private String commission;
        private String unitsTraded;
        private String minimumCommission;
    }

    private String name;
    private InstrumentType type;
    private String displayName;
    private Integer pipLocation;
    private Integer displayPrecision;
    private Integer tradeUnitsPrecision;
    private String minimumTradeSize;
    private String maximumTrailingStopDistance;
    private String minimumTrailingStopDistance;
    private String maximumPositionSize;
    private String maximumOrderUnits;
    private String marginRate;
    private InstrumentCommission commission;

}