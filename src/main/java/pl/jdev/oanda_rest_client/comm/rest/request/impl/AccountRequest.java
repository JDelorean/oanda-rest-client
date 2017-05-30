package pl.jdev.oanda_rest_client.comm.rest.request.impl;

import java.net.URISyntaxException;

import org.apache.http.HttpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountRequest extends RequestImpl {

	private final static Logger LOGGER = LogManager.getLogger(AccountRequest.class);

	/**
	 * Get a list of all Accounts authorized for the provided token.
	 *
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getAccountsForToken() throws URISyntaxException {
		LOGGER.debug("Requesting all Accounts for Token.");
		return super.getRequestFactory().newGETRequest("/accounts", null);
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
		LOGGER.debug("Requesting Account details.");
		return super.getRequestFactory().newGETRequest("/accounts/${account}", null);
	}

	/**
	 * Get a summary for a single Account that a client has access to.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest getAccountSummary() throws URISyntaxException {
		LOGGER.debug("Requesting Account summary.");
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
		LOGGER.debug("Requesting Account's tradeable instruments.");
		return super.getRequestFactory().newGETRequest("/accounts/${account}/instruments", null);
	}

	/**
	 * Set the client-configurable portions of an Account.
	 * 
	 * @return
	 * @throws URISyntaxException
	 */
	public HttpRequest setAccountConfiguration() throws URISyntaxException {
		LOGGER.debug("Requesting Account's tradeable instruments.");
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
		LOGGER.debug("Requesting Account's state and changes.");
		return super.getRequestFactory().newGETRequest("/accounts/${account}/changes", null);
	}

}
