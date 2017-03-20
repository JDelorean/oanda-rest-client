package pl.jdev.oanda_rest_client.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class Request {

	private Properties prop = new Properties();
	private String propFileName = "fxapp.properties";
	private InputStream inputStream;

	private final String ACCOUNT = prop.getProperty("oanda.accountId");
	private final String CONTENT_TYPE = prop.getProperty("oanda.contentType");
	private final String TOKEN = prop.getProperty("oanda.token");
	private final String URL = prop.getProperty("oanda.url");

	private String header;
	private String body;

	public Request() {
		try {
			this.setProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getHeader() {
		return header;
	}

	public String getBody() {
		return body;
	}

	private void setProperties() throws IOException {
		inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		prop.load(inputStream);
	}

	public String getCONTENT_TYPE() {
		return CONTENT_TYPE;
	}

	public String getTOKEN() {
		return TOKEN;
	}

	public String getURL() {
		return URL;
	}

	public String getACCOUNT() {
		return ACCOUNT;
	}

}
