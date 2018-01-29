package pl.jdev.oanda_rest_client.domain.order;

import java.util.Date;

import pl.jdev.oanda_rest_client.domain.ClientExtensions;
import pl.jdev.oanda_rest_client.domain.order.Order.TimeInForce;

public class FillingOrderDetails {
    private String price;
    private TimeInForce timeInForce;
    private Date gtdTime;
    private ClientExtensions clientExtensions;
}