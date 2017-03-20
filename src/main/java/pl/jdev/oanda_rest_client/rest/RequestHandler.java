package pl.jdev.oanda_rest_client.rest;

import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	
	HttpClient client = HttpClientBuilder.create().build();
	
	public HttpResponse process(HttpRequest request) throws ClientProtocolException, IOException{
		logger.debug("Processing HTTP request...");
		return client.execute((HttpUriRequest) request);
	}

}
