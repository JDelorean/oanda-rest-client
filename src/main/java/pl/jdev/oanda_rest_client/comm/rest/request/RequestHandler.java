package pl.jdev.oanda_rest_client.comm.rest.request;

import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestHandler {

	private final static Logger LOGGER = LogManager.getLogger(RequestHandler.class);

	HttpClient client = HttpClientBuilder.create().build();

	public HttpResponse send(HttpRequest request) throws ClientProtocolException, IOException {
		LOGGER.debug("Sending HTTP request {}...", request.toString());
		HttpResponse response = client.execute((HttpUriRequest) request);
		LOGGER.debug("Message send complete. Returning response {}.", response);
		return response;
	}

}
