package pl.jdev.oanda_rest_client.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppProperties {

	private final static Logger logger = LoggerFactory.getLogger(AppProperties.class);

	private static AppProperties instance = null;

	private Properties prop = new Properties();
	private String propFileName = "fxapp.properties";
	private InputStream inputStream;

	// properties
	private String account;
	private String contentType;
	private String token;
	private String url;
	private String dateTimeFormat;

	protected AppProperties() throws IOException {
		logger.debug("Instantiating...");
		inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		prop.load(inputStream);
		account = prop.getProperty("oanda.accountId");
		logger.info("Oanda Account [" + account + "].");
		contentType = prop.getProperty("oanda.contentType");
		logger.info("Entity Content Type [" + contentType + "].");
		token = prop.getProperty("oanda.token");
		logger.info("Oanda Token [" + token + "].");
		url = prop.getProperty("oanda.url.practice");
		logger.info("Oanda base URL [" + url + "].");
		dateTimeFormat = prop.getProperty("oanda.dateTimeFormat");
		logger.info("Date/Time format [" + dateTimeFormat + "].");
		logger.debug("Instantiated successfully.");
	}

	public static AppProperties getInstance() throws IOException {
		if (instance == null) {
			instance = new AppProperties();
		}
		return instance;
	}

	public String getAccount() {
		return account;
	}

	public String getContentType() {
		return contentType;
	}

	public String getToken() {
		return token;
	}

	public String getUrl() {
		return url;
	}

	public String getDateTimeFormat() {
		return dateTimeFormat;
	}

}
