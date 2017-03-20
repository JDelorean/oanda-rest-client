package pl.jdev.oanda_rest_client.task;

import java.io.IOException;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkConnectionCheck implements Runnable {

	private final static Logger logger = LoggerFactory.getLogger(NetworkConnectionCheck.class);

	private InetAddress address;

	private boolean googleFlag = false;
	private boolean oandaFlag = false;

	@Override
	public void run() {
		try {
			googleFlag = pingAddress("bbh.com");
			logger.debug(String.valueOf(googleFlag));
			oandaFlag = pingAddress("oanda.com");
			logger.debug(String.valueOf(oandaFlag));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean pingAddress(String address) throws IOException {
		logger.debug("Pinging address [" + address + "].");
		this.address = InetAddress.getByName(address);
		return this.address.isReachable(5000);
	}

}
