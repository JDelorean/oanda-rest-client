package pl.jdev.oanda_rest_client.domain.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;
import pl.jdev.oanda_rest_client.domain.ClientExtensions;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class Trade extends AbstractEntity implements Serializable {

    public enum TradeState {
        OPEN, CLOSED, CLOSE_WHEN_TRADEABLE;
    }

    private String tradeId;
    private Instrument instrument;
    private Double price;
    private Date openTime;
    private TradeState state;
    private Double initialUnits;
    private Double currentUnits;
    private Double realizedPL;
    private Double unrealizedPL;
    private Double averageClosePrice;
    private List<String> closingTransactionIDs;
    private Double financing;
    private Date closeTime;
    private ClientExtensions clientExtensions;
    // private Order takeProfitOrder;
    // private Order stopLossOrder;
    // private Order trailingStopLossOrder;

}