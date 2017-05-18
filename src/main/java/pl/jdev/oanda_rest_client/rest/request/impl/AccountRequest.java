package pl.jdev.oanda_rest_client.rest.request.impl;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountRequest extends RequestImpl {

	private final static Logger logger = LoggerFactory.getLogger(AccountRequest.class);

	/**
	 * Get a list of all Accounts authorized for the provided token.
	 *
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getAccountsForToken() throws URISyntaxException {
		logger.info("Requesting all Accounts for Token.");
		HttpRequest request = super.getRequestFactory().newGETRequest("/accounts", null);
		try {
			System.out.println(super.getRequestHandler().process(request).toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get the full details for a single Account that a client has access to.
	 * Full pending Order, open Trade and open Position representations are
	 * provided.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getAccount() throws URISyntaxException {
		logger.info("Requesting Account details.");
		return super.getRequestFactory().newGETRequest("/accounts/${account}", null);
	}

	/**
	 * Get a summary for a single Account that a client has access to.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getAccountSummary() throws URISyntaxException {
		logger.info("Requesting Account summary.");
		return super.getRequestFactory().newGETRequest("/accounts/${account}/summary", null);
	}

	/**
	 * Get the list of tradeable instruments for the given Account. The list of
	 * tradeable instruments is dependent on the regulatory division that the
	 * Account is located in, thus should be the same for all Accounts owned by
	 * a single user.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getAccountInstruments() throws URISyntaxException {
		logger.info("Requesting Account's tradeable instruments.");
		return super.getRequestFactory().newGETRequest("/accounts/${account}/instruments", null);
	}

	/**
	 * Set the client-configurable portions of an Account.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest setAccountConfiguration() throws URISyntaxException {
		// logger.info("Requesting Account's tradeable instruments.");
		return super.getRequestFactory().newGETRequest("/accounts/${account}/configuration", null);
	}

	/**
	 * Endpoint used to poll an Account for its current state and changes since
	 * a specified TransactionID.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getAccountChanges() throws URISyntaxException {
		logger.info("Requesting Account's state and changes.");
		return super.getRequestFactory().newGETRequest("/accounts/${account}/changes", null);
	}

}
