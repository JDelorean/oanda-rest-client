package pl.jdev.oanda_rest_client.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Data
public class ClientExtensions {
    private UUID id;
    private String tag;
    private String comment;

    @Builder(builderMethodName = "newClientExtensions")
    public ClientExtensions(String tag, String comment) {
        this.id = randomUUID();
        this.tag = tag;
        this.comment = comment;
    }

    @Builder
    public ClientExtensions(UUID id, String tag, String comment) {
        this.id = id;
        this.tag = tag;
        this.comment = comment;
    }
}