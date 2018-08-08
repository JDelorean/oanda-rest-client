package pl.jdev.oanda_rest_client.rest.converter;

import com.fasterxml.jackson.databind.JsonNode;
import pl.jdev.oanda_rest_client.domain.ClientExtensions;

import java.util.UUID;
import java.util.function.Function;

public class OrderDeserializationTools {

    Function<JsonNode, ClientExtensions> deserializeClientExtensions = clientExtensionsNode -> {
        return ClientExtensions.builder()
                .id(UUID.fromString(((JsonNode) clientExtensionsNode.get("clientExtensions").get("id")).textValue()))
                .tag(((JsonNode) clientExtensionsNode.get("clientExtensions").get("tag")).textValue())
                .comment(((JsonNode) clientExtensionsNode.get("clientExtensions").get("comment")).textValue())
                .build();
    };

}
