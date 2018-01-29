package pl.jdev.oanda_rest_client.domain.trade;

import java.util.Date;
import java.util.List;

import pl.jdev.oanda_rest_client.domain.ClientExtensions;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;

public class Trade {

    enum TradeState {
        OPEN, CLOSED, CLOSE_WHEN_TRADEABLE;
    }

    private String id;
    private Instrument instrument;
    private String price;
    private Date openTime;
    private TradeState state;
    private String initialUnits;
    private String currentUnits;
    private String realizedPL;
    private String unrealizedPL;
    private String averageClosePrice;
    private List<String> closingTransactionIDs;
    private String financing;
    private Date closeTime;
    private ClientExtensions clientExtensions;
    // private Order takeProfitOrder;
    // private Order stopLossOrder;
    // private Order trailingStopLossOrder;
}