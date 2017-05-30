package pl.jdev.oanda_rest_client.main.task;

import java.io.IOException;
import java.net.InetAddress;
import java.util.AbstractMap.SimpleEntry;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetworkConnectionCheck extends KafkaProducer<String, SimpleEntry<String, Boolean>> implements Runnable {

	private final static Logger LOGGER = LogManager.getLogger(NetworkConnectionCheck.class);

	private InetAddress address;

	private boolean googleFlag = false;
	private boolean oandaDemoFlag = false;
	private boolean oandaLiveFlag = false;

	public NetworkConnectionCheck(Properties properties) {
		super(properties);
	}

	@Override
	public void run() {
		try {
			googleFlag = pingAddress("google.com");
			super.send(new ProducerRecord<String, SimpleEntry<String, Boolean>>("networkConn",
					new SimpleEntry<String, Boolean>("google.com", googleFlag)));
			oandaDemoFlag = pingAddress("api-fxpractice.oanda.com");
			super.send(new ProducerRecord<String, SimpleEntry<String, Boolean>>("networkConn",
					new SimpleEntry<String, Boolean>("api-fxpractice.oanda.com", oandaDemoFlag)));
			oandaLiveFlag = pingAddress("api-fxtrade.oanda.com");
			super.send(new ProducerRecord<String, SimpleEntry<String, Boolean>>("networkConn",
					new SimpleEntry<String, Boolean>("api-fxtrade.oanda.com", oandaLiveFlag)));
		} catch (IOException e) {
			LOGGER.error("Unable to ping address {}\n{}", e.getMessage(), e);
		} finally {
			googleFlag = false;
			oandaDemoFlag = false;
			oandaLiveFlag = false;
		}
	}

	private boolean pingAddress(String address) throws IOException {
		LOGGER.debug("Pinging address {}...", address);

		this.address = InetAddress.getByName(address);
		boolean isReachable = this.address.isReachable(5000);

		LOGGER.debug("{} is reachable.", address);
		return isReachable;
	}

}
