package pl.jdev.oanda_rest_client.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.jdev.oanda_rest_client.main.AppProperties;

public class RequestFactory {

	private final static Logger logger = LoggerFactory.getLogger(RequestFactory.class);

	private static RequestFactory instance = null;

	private String account;
	private String contentType;
	private String token;
	private String url;

	protected RequestFactory() throws IOException {
		logger.debug("Instantiating...");
		AppProperties properties = AppProperties.getInstance();
		account = properties.getAccount();
		contentType = properties.getContentType();
		token = properties.getToken();
		url = properties.getUrl();
		logger.debug("Instantiated successfully.");
	}

	public static RequestFactory getInstance() throws IOException {
		if (instance == null) {
			instance = new RequestFactory();
		}
		return instance;
	}

	public HttpRequest newGETRequest(String uriTrailerTemplate, Map<String, String> valuesMap) throws URISyntaxException {
		logger.debug("Creating new GET request...");
		HttpGet getRequest = new HttpGet();
		getRequest.addHeader("Content-Type", contentType);
		getRequest.addHeader("Authorization", "Bearer " + token);
		if (valuesMap == null) {
			valuesMap = new HashMap<String, String>();
		}
		valuesMap.put("account", account);
		StrSubstitutor substitutor = new StrSubstitutor(valuesMap);
		String subTrailer = substitutor.replace(uriTrailerTemplate);
		String requestURI = String.format(url + subTrailer);
		URI oandaURI = new URI(requestURI);
		getRequest.setURI(oandaURI);
		logger.debug(printRequest(getRequest));
		return getRequest;
	}
	
	private String printRequest(HttpRequest request){
		String print = "\n";
		for(Header header : request.getAllHeaders()){
			print = print + header.toString() + "\n";
		}
		print = print + request.toString();
		return print;
	}

}
