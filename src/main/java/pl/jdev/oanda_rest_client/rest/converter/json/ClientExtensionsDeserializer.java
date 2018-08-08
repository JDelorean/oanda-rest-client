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

import java.io.IOException;
import java.util.UUID;

@JsonComponent
@Log
public class ClientExtensionsDeserializer extends JsonDeserializer<ClientExtensions> {
    @Override
    public ClientExtensions deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        log.info(String.format("Deserializing: %s, to: %s", jsonNode, ClientExtensions.class));
        TreeNode clientExtensionsNode = jsonNode.get("clientExtensions");
        ClientExtensions clientExtensions = ClientExtensions.builder()
                .id(UUID.fromString(((JsonNode) clientExtensionsNode.get("clientExtensions").get("id")).textValue()))
                .tag(((JsonNode) clientExtensionsNode.get("clientExtensions").get("tag")).textValue())
                .comment(((JsonNode) clientExtensionsNode.get("clientExtensions").get("comment")).textValue())
                .build();
        log.info(String.format("Deserialized object: %s", clientExtensions.toString()));
        return clientExtensions;
    }
}
