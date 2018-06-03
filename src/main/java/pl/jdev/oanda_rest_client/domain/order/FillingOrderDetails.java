package pl.jdev.oanda_rest_client.domain.order;

import java.util.Date;

import pl.jdev.oanda_rest_client.domain.ClientExtensions;
import pl.jdev.oanda_rest_client.domain.order.Order.TimeInForce;

public class FillingOrderDetails {
	private String price;
	private TimeInForce timeInForce;
	private Date gtdTime;
	private ClientExtensions clientExtensions;

	public FillingOrderDetails() {

	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public TimeInForce getTimeInForce() {
		return timeInForce;
	}

	public void setTimeInForce(TimeInForce timeInForce) {
		this.timeInForce = timeInForce;
	}

	public Date getGtdTime() {
		return gtdTime;
	}

	public void setGtdTime(Date gtdTime) {
		this.gtdTime = gtdTime;
	}

	public ClientExtensions getClientExtensions() {
		return clientExtensions;
	}

	public void setClientExtensions(ClientExtensions clientExtensions) {
		this.clientExtensions = clientExtensions;
	}

}