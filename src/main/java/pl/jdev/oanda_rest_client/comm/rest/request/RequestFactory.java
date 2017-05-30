package pl.jdev.oanda_rest_client.comm.rest.request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.jdev.oanda_rest_client.main.AppProperties;

public class RequestFactory {

	private final static Logger LOGGER = LogManager.getLogger(RequestFactory.class);

	private static RequestFactory instance = null;

	private String account;
	private String contentType;
	private String token;
	private String url;

	protected RequestFactory() throws IOException {
		LOGGER.debug("Instantiating...");

		AppProperties properties = AppProperties.getInstance();
		account = properties.get("oanda.accountId");
		contentType = properties.get("oanda.contentType");
		token = properties.get("oanda.token");
		url = properties.get("oanda.url.practice");

		LOGGER.debug("Instantiated successfully.");
	}

	public static RequestFactory getInstance() throws IOException {
		if (instance == null) {
			instance = new RequestFactory();
		}
		return instance;
	}

	/**
	 * Creates and returns a new HTTP GET request for the specified URI.
	 * 
	 * @param uriTrailer
	 *            HTTP URI trailer. May not be 'null' or empty String "".
	 *            Example: "/accounts/{accountID}/trades".
	 * @param values
	 *            String elements to replace URI template placeholders. Example:
	 *            "/accounts/${accountID}/trades" > "/accounts/123456/trades".
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest newGETRequest(String uriTrailer, Map<String, String> values) throws URISyntaxException, NullPointerException {
		LOGGER.debug("Creating new GET request for URI trailer {} with values {}...", uriTrailer, values);

		validateUri(uriTrailer);
		HttpGet getRequest = new HttpGet();
		getRequest.addHeader("Content-Type", contentType);
		getRequest.addHeader("Authorization", "Bearer " + token);
		URI oandaURI = buildUri(uriTrailer, values);
		getRequest.setURI(oandaURI);

		LOGGER.debug("... created new GET request:\n{}", printRequest(getRequest));
		return getRequest;
	}

	/**
	 * Creates and returns a new HTTP PUT request for the specified URI.
	 * 
	 * @param uriTrailer
	 *            HTTP URI trailer. May not be 'null' or empty String "".
	 *            Example: "/accounts/{accountID}/trades".
	 * @param values
	 *            String elements to replace URI template placeholders. Example:
	 *            "/accounts/${accountID}/trades" > "/accounts/123456/trades".
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest newPUTRequest(String uriTrailer, Map<String, String> values) throws URISyntaxException, NullPointerException {
		LOGGER.debug("Creating new PUT request for URI trailer {} with values {}...", uriTrailer, values);

		validateUri(uriTrailer);

		HttpPut putRequest = new HttpPut();
		putRequest.addHeader("Content-Type", contentType);
		putRequest.addHeader("Authorization", "Bearer " + token);
		URI oandaUri = buildUri(uriTrailer, values);
		putRequest.setURI(oandaUri);

		LOGGER.debug("... created new PUT request:\n{}", printRequest(putRequest));
		return putRequest;
	}

	/**
	 * Creates and returns a new HTTP POST request for the specified URI.
	 * 
	 * @param uriTrailer
	 *            HTTP URI trailer. May not be 'null' or empty String "".
	 *            Example: "/accounts/{accountID}/trades".
	 * @param values
	 *            String elements to replace URI template placeholders. Example:
	 *            "/accounts/${accountID}/trades" > "/accounts/123456/trades".
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest newPOSTRequest(String uriTrailer, Map<String, String> values) throws URISyntaxException, NullPointerException {
		LOGGER.debug("Creating new POST request for URI trailer {} with values {}...", uriTrailer, values);

		validateUri(uriTrailer);

		HttpPost postRequest = new HttpPost();
		postRequest.addHeader("Content-Type", contentType);
		postRequest.addHeader("Authorization", "Bearer " + token);
		URI oandaUri = buildUri(uriTrailer, values);
		postRequest.setURI(oandaUri);

		LOGGER.debug("... created new POST request:\n{}", printRequest(postRequest));
		return postRequest;
	}

	/**
	 * Creates and returns a new HTTP PATCH request for the specified URI.
	 * 
	 * @param uriTrailer
	 *            HTTP URI trailer. May not be 'null' or empty String "".
	 *            Example: "/accounts/{accountID}/trades".
	 * @param values
	 *            String elements to replace URI template placeholders. Example:
	 *            "/accounts/${accountID}/trades" > "/accounts/123456/trades".
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest newPATCHRequest(String uriTrailer, Map<String, String> values) throws URISyntaxException, NullPointerException {
		LOGGER.debug("Creating new PATCH request for URI trailer {} with values {}...", uriTrailer, values);

		validateUri(uriTrailer);

		HttpPatch patchRequest = new HttpPatch();
		patchRequest.addHeader("Content-Type", contentType);
		patchRequest.addHeader("Authorization", "Bearer " + token);
		URI oandaUri = buildUri(uriTrailer, values);
		patchRequest.setURI(oandaUri);

		LOGGER.debug("... created new PATCH request:\n{}", printRequest(patchRequest));
		return patchRequest;
	}

	private void validateUri(String uriTrailer) throws URISyntaxException, NullPointerException {
		LOGGER.debug("Validating URI trailer {}...", uriTrailer);
		// Throw exception if URI trailer is an empty String or null;
		if (uriTrailer == null) {
			LOGGER.error("URI trailer is NULL.");
			throw new NullPointerException("Value of 'uriTrailer' may not be null.");
		} else if (uriTrailer.isEmpty()) {
			LOGGER.error("URI trailer is an empty String.");
			throw new URISyntaxException(uriTrailer, "Value of 'uriTrailer' may not be an empty string.");
		}
		LOGGER.debug("URI trailer is valid.");
	}

	private URI buildUri(String uriTrailer, Map<String, String> values) throws URISyntaxException {
		LOGGER.debug("Building URI for trailer {} with values {}...", uriTrailer, values);

		if (values == null) {
			values = new HashMap<String, String>();
		}
		values.put("account", account);
		StrSubstitutor substitutor = new StrSubstitutor(values);
		String subTrailer = substitutor.replace(uriTrailer);
		String requestURI = String.format(url + subTrailer);
		URI oandaURI = new URI(requestURI);

		LOGGER.debug("Returning URI {}", oandaURI);
		return oandaURI;
	}

	private String printRequest(HttpRequest request) {
		String print = "";
		for (Header header : request.getAllHeaders()) {
			print = print + header.toString() + "\n";
		}
		print = print + request.toString();
		return print;
	}

}
