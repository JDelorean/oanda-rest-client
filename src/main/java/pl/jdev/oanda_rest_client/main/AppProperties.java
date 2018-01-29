package pl.jdev.oanda_rest_client.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppProperties {

	private final static Logger LOGGER = LoggerFactory.getLogger(AppProperties.class);

	private static AppProperties instance = null;

	private Properties properties = new Properties();
	private final String OANDA_PROP = "oanda_rest_client.properties";
	private final String HIBERNATE_PROP = "hibernate.properties";
	private final String KAFKA_PROP = "kafka.properties";
	private final String EMAIL_PROP = "email.properties";
	private InputStream inputStream;

	protected AppProperties() {
		LOGGER.debug("Instantiating...");

		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(OANDA_PROP);
			properties.load(inputStream);
			inputStream = getClass().getClassLoader().getResourceAsStream(HIBERNATE_PROP);
			properties.load(inputStream);
			inputStream = getClass().getClassLoader().getResourceAsStream(KAFKA_PROP);
			properties.load(inputStream);
			inputStream = getClass().getClassLoader().getResourceAsStream(EMAIL_PROP);
			properties.load(inputStream);
		} catch (IOException e) {
			LOGGER.warn("Unable to read properties: {}", e.getMessage());
		} finally {
			LOGGER.debug("Loaded properties: \n{}", properties);
		}

		LOGGER.debug("Instantiated successfully.");
	}

	public static AppProperties getInstance() {
		if (instance == null) {
			instance = new AppProperties();
		}
		return instance;
	}

	public String get(String propertName) {
		return properties.getProperty(propertName);
	}

	public Properties getAll() {
		return properties;
	}

}
