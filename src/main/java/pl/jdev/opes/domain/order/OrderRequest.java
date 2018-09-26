package pl.jdev.opes.domain.order;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.jdev.opes.domain.AbstractEntity;
import pl.jdev.opes.domain.order.types.market_order.MarketOrderRequest;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true,
        defaultImpl = MarketOrderRequest.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MarketOrderRequest.class, name = "MARKET")
//        @JsonSubTypes.Type(value = Dog.class, name = "dog") //TODO: complete the list
})
public abstract class OrderRequest extends AbstractEntity implements Serializable {

}