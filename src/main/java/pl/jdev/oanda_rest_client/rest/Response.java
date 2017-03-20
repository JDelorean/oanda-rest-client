package pl.jdev.oanda_rest_client.rest;

import org.apache.http.HttpResponse;

public class Response {

	private HttpResponse response;

	public Response(HttpResponse response) {
		this.response = response;
	}

}
