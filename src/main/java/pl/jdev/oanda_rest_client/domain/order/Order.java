package pl.jdev.oanda_rest_client.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;
import pl.jdev.oanda_rest_client.domain.ClientExtensions;

import java.util.Date;

@Data
@AllArgsConstructor
@TypeAlias("order")
public abstract class Order extends AbstractEntity {
    @Indexed
    private String orderId;
    private Date createTime;
    private OrderState state;
    private ClientExtensions clientExtensions;
}