package pl.jdev.oanda_rest_client.rest.converter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.java.Log;
import org.springframework.boot.jackson.JsonComponent;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.domain.instrument.Instrument;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

import java.io.IOException;
import java.sql.Date;

@JsonComponent
@Log
public class TradeJsonDeserializer extends JsonDeserializer<Trade> {

    @Override
    public Trade deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        TreeNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        log.info(String.format("Deserializing: %s", jsonNode));
        log.info(String.format("To: %s", Account.class));
        Trade trade;
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);

        JsonNode tradeNode = node.get("trade");
        trade = Trade.builder()
                .tradeId(tradeNode.get("id").textValue())
                .instrument(new Instrument())
                .price(tradeNode.get("price").asDouble())
                .openTime(Date.valueOf(tradeNode.get("openTime").textValue()))
                .state(Trade.TradeState.valueOf(tradeNode.get("state").textValue()))
                .initialUnits(tradeNode.get("initialUnits").asDouble())
                .currentUnits(tradeNode.get("currentUnits").asDouble())
                .realizedPL(tradeNode.get("realizedPL").asDouble())
                .unrealizedPL(tradeNode.get("unrealizedPL").asDouble())
                .financing(tradeNode.get("financing").asDouble())
                .build();
        return trade;
    }
}
