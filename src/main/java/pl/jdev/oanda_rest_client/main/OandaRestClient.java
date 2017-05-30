package pl.jdev.oanda_rest_client.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pl.jdev.oanda_rest_client.domain.instrument.CurrencyPair;
import pl.jdev.oanda_rest_client.main.task.PricingStreamHandler;
import pl.jdev.oanda_rest_client.main.task.TaskManager;

public class OandaRestClient {

	private final static Logger LOGGER = LogManager.getLogger(OandaRestClient.class);

	public static void main(String[] args) {
		LOGGER.info("Initiating oanda-rest-client application...");

		AppProperties prop = AppProperties.getInstance();

		// NetworkConnectionCheck netConnCheckTask = new
		// NetworkConnectionCheck(prop.getAll());

		PricingStreamHandler pricing = new PricingStreamHandler(new CurrencyPair("EUR", "USD"));

		TaskManager taskMngr = new TaskManager(1);

		taskMngr.run(pricing);

		// taskMngr.scheduleAtFixedRate(netConnCheckTask, 30);
	}
}
