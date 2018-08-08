package pl.jdev.oanda_rest_client.rest.converter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.java.Log;
import pl.jdev.oanda_rest_client.domain.account.Account;
import pl.jdev.oanda_rest_client.domain.account.GuaranteedStopLossOrderMode;
import pl.jdev.oanda_rest_client.domain.order.Order;
import pl.jdev.oanda_rest_client.domain.position.Position;
import pl.jdev.oanda_rest_client.domain.trade.Trade;

import java.io.IOException;
import java.time.Instant;
import java.util.Currency;
import java.util.Date;
import java.util.List;

//@JsonComponent
@Log
public class AccountJsonDeserializer extends JsonDeserializer<Account> {

    @Override
    public Account deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        log.info(String.format("Deserializing: %s", jsonNode));
        log.info(String.format("To: %s", Account.class));
        TreeNode accountNode = jsonNode.get("object");

        TypeReference<List<Trade>> tradeMapType = new TypeReference<List<Trade>>() {
        };
        JsonParser tradeParser = accountNode.get("trades").traverse();
        List<Trade> trades = jsonParser.getCodec().readValue(tradeParser, tradeMapType);

        TypeReference<List<Position>> positionMapType = new TypeReference<List<Position>>() {
        };
        JsonParser positionParser = accountNode.get("positions").traverse();
        List<Position> positions = jsonParser.getCodec().readValue(positionParser, positionMapType);

        TypeReference<List<Order>> orderMapType = new TypeReference<List<Order>>() {
        };
        JsonParser orderParser = accountNode.get("orders").traverse();
        List<Order> orders = jsonParser.getCodec().readValue(orderParser, orderMapType);

        Account account = Account.builder()
                .accountId(((JsonNode) accountNode.get("id")).textValue())
                .alias(((JsonNode) accountNode.get("alias")).textValue())
                .currency(Currency.getInstance(((JsonNode) accountNode.get("currency")).textValue()))
                .balance(((JsonNode) accountNode.get("balance")).asDouble())
                .createdByUserID(((JsonNode) accountNode.get("createdByUserID")).textValue())
                .guaranteedStopLossOrderMode(GuaranteedStopLossOrderMode.valueOf(((JsonNode) accountNode.get("guaranteedStopLossOrderMode")).textValue()))
                .createdTime(Date.from(Instant.parse(((JsonNode) accountNode.get("createdTime")).textValue())))
                .pl(((JsonNode) accountNode.get("pl")).asDouble())
                .resettablePL(((JsonNode) accountNode.get("resettablePL")).asDouble())
                .resettablePLTime(Date.from(Instant.parse(((JsonNode) accountNode.get("resettablePLTime")).textValue())))
                .marginRate(((JsonNode) accountNode.get("marginRate")).asDouble())
// TODO: Following are conditional fields
// .marginCallEnterTime(Date.from(Instant.parse(((JsonNode) accountNode.getOrder("marginCallEnterTime")).textValue())))
//                .marginCallExtensionCount(((JsonNode) accountNode.getOrder("marginCallExtensionCount")).asInt())
//                .lastMarginCallExtensionTime(Date.from(Instant.parse(((JsonNode) accountNode.getOrder("lastMarginCallExtensionTime")).textValue())))
                .openTradeCount(((JsonNode) accountNode.get("openTradeCount")).asInt())
                .openPositionCount(((JsonNode) accountNode.get("openPositionCount")).asInt())
                .pendingOrderCount(((JsonNode) accountNode.get("pendingOrderCount")).asInt())
                .hedgingEnabled(((JsonNode) accountNode.get("hedgingEnabled")).asBoolean())
                .unrealizedPL(((JsonNode) accountNode.get("unrealizedPL")).asDouble())
                .nav(((JsonNode) accountNode.get("NAV")).asDouble())
                .marginUsed(((JsonNode) accountNode.get("marginUsed")).asDouble())
                .marginAvailable(((JsonNode) accountNode.get("marginAvailable")).asDouble())
                .positionValue(((JsonNode) accountNode.get("positionValue")).asDouble())
                .marginCloseoutUnrealizedPL(((JsonNode) accountNode.get("marginCloseoutUnrealizedPL")).asDouble())
                .marginCloseoutNAV(((JsonNode) accountNode.get("marginCloseoutNAV")).asDouble())
                .marginCloseoutMarginUsed(((JsonNode) accountNode.get("marginCloseoutMarginUsed")).asDouble())
                .marginCloseoutPercent(((JsonNode) accountNode.get("marginCloseoutPercent")).asDouble())
                .marginCloseoutPositionValue(((JsonNode) accountNode.get("marginCloseoutPositionValue")).asDouble())
                .withdrawalLimit(((JsonNode) accountNode.get("withdrawalLimit")).asDouble())
                .marginCallMarginUsed(((JsonNode) accountNode.get("marginCallMarginUsed")).asDouble())
                .marginCallPercent(((JsonNode) accountNode.get("marginCallPercent")).asDouble())
//                .trades(trades)
//                .positions(positions)
//                .orders(orders)
                .build();
        log.info(String.format("Deserialized to: %s", account.toString()));
        return account;
    }
}
