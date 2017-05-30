package pl.jdev.oanda_rest_client.domain;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.jdev.oanda_rest_client.main.AppProperties;

public class AppDateTime {

	private Date date;
	private String appDateTime;
	private static String dateTimeFormat;

	public AppDateTime(Date date) throws IOException {
		this.date = date;
		dateTimeFormat = AppProperties.getInstance().get("oanda.dateTimeFormat");
	}

	@Override
	public String toString() {
		return new SimpleDateFormat(dateTimeFormat).format(date);
	}

	public static String getCurrentTimeStamp() throws IOException {
		return new SimpleDateFormat(dateTimeFormat).format(new Date());
	}

}
