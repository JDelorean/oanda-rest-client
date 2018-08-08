package pl.jdev.oanda_rest_client.domain.order;

import lombok.Builder;
import lombok.Data;
import pl.jdev.oanda_rest_client.domain.ClientExtensions;

import java.util.Date;

@Data
@Builder
public class FillingOrderDetails {
    private String price;
    private TimeInForce timeInForce;
    private Date gtdTime;
    private ClientExtensions clientExtensions;
}