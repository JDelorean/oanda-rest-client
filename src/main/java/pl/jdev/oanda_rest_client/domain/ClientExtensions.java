package pl.jdev.oanda_rest_client.domain;

import static java.util.UUID.randomUUID;

import java.util.UUID;

import lombok.Data;

@Data
public class ClientExtensions {
	private final UUID id;
	private String tag;
	private String comment;

	public ClientExtensions(String tag, String comment) {
		this.id = randomUUID();
		this.tag = tag;
		this.comment = comment;
	}
}