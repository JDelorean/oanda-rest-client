package pl.jdev.oanda_rest_client.rest.converter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.java.Log;
import org.springframework.boot.jackson.JsonComponent;
import pl.jdev.oanda_rest_client.domain.ClientExtensions;
import pl.jdev.oanda_rest_client.domain.order.OrderPositionFill;
import pl.jdev.oanda_rest_client.domain.order.OrderState;
import pl.jdev.oanda_rest_client.domain.order.TimeInForce;
import pl.jdev.oanda_rest_client.domain.order.types.market_order.MarketOrder;
import pl.jdev.oanda_rest_client.domain.order.types.market_order.MarketOrderDelayedTradeClose;
import pl.jdev.oanda_rest_client.domain.order.types.market_order.MarketOrderPositionCloseout;
import pl.jdev.oanda_rest_client.domain.order.types.market_order.MarketOrderTradeClose;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@JsonComponent
@Log
public class MarketOrderJsonDeserializer extends JsonDeserializer<MarketOrder> {
    @Override
    public MarketOrder deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        log.info(String.format("Deserializing: %s", jsonNode));
        log.info(String.format("To: %s", MarketOrder.class));
        TreeNode orderNode = jsonNode.get("order");

        MarketOrder order = MarketOrder.builder()
                .id(((JsonNode) orderNode.get("id")).textValue())
                .createTime(Date.from(Instant.parse(((JsonNode) orderNode.get("createTime")).textValue())))
                .state(OrderState.valueOf(((JsonNode) orderNode.get("state")).textValue()))
//                .clientExtensions(ClientExtensions.builder()
//                        .accountId(UUID.fromString(((JsonNode) orderNode.getOrder("clientExtensions").getOrder("accountId")).textValue()))
//                        .tag(((JsonNode) orderNode.getOrder("clientExtensions").getOrder("tag")).textValue())
//                        .comment(((JsonNode) orderNode.getOrder("clientExtensions").getOrder("comment")).textValue())
//                        .build())
                .clientExtensions((ClientExtensions) deserializationContext.findRootValueDeserializer(deserializationContext.constructType(ClientExtensions.class)).deserialize(((JsonParser) orderNode.get("clientExtensions")), deserializationContext))
                .instrument(((JsonNode) orderNode.get("instrument")).textValue())
                .units(((JsonNode) orderNode.get("units")).textValue())
                .timeInForce(TimeInForce.valueOf(((JsonNode) orderNode.get("timeInForce")).textValue()))
                .priceBound(((JsonNode) orderNode.get("priceBound")).textValue())
                .positionFill(OrderPositionFill.valueOf(((JsonNode) orderNode.get("positionFill")).textValue()))
                .tradeClose(MarketOrderTradeClose.builder()
                        .tradeID(((JsonNode) orderNode.get("tradeClose").get("tradeID")).textValue())
                        .clientTradeID(((JsonNode) orderNode.get("tradeClose").get("clientTradeID")).textValue())
                        .units(((JsonNode) orderNode.get("tradeClose").get("units")).textValue())
                        .build())
                .longPositionCloseout(MarketOrderPositionCloseout.builder()
                        .instrument(((JsonNode) orderNode.get("longPositionCloseout").get("instrument")).textValue())
                        .units(((JsonNode) orderNode.get("longPositionCloseout").get("units")).textValue())
                        .build())
                .shortPositionCloseout(MarketOrderPositionCloseout.builder()
                        .instrument(((JsonNode) orderNode.get("shortPositionCloseout").get("instrument")).textValue())
                        .units(((JsonNode) orderNode.get("shortPositionCloseout").get("units")).textValue())
                        .build())
//                .marginCloseout(MarketOrderMarginCloseout
////                        .withReason(MarketOrderMarginCloseoutReason.valueOf(((JsonNode) orderNode.getOrder("marginCloseout").getOrder("reason")).textValue()))
//                        .build())
                .delayedTradeClose(MarketOrderDelayedTradeClose.builder()
                        .tradeID(((JsonNode) orderNode.get("delayedTradeClose").get("tradeID")).textValue())
                        .clientTradeID(((JsonNode) orderNode.get("delayedTradeClose").get("clientTradeID")).textValue())
                        .units(((JsonNode) orderNode.get("delayedTradeClose").get("units")).textValue())
                        .build())
//                .takeProfitOnFill(FillingOrderDetails.builder()
//                        .price(((JsonNode) orderNode.getOrder("takeProfitOnFill").getOrder("price")).textValue())
//                        .timeInForce()
//                        .gtdTime()
//                        .clientExtensions()
//                        .build())
                .build();
        return null;
        //TODO: finish HIM!


    }
}
