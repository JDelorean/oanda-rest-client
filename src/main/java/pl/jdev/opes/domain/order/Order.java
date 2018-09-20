package pl.jdev.opes.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import pl.jdev.opes.domain.AbstractEntity;
import pl.jdev.opes.domain.ClientExtensions;

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