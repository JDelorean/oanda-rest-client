package pl.jdev.oanda_rest_client.service.oanda_service.order;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.order.Order;
import pl.jdev.oanda_rest_client.domain.order.OrderRequest;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonOrderListWrapper;
import pl.jdev.oanda_rest_client.service.data_access_layer.OrderDAL;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log
public class OandaOrderService extends AbstractOandaService<Order> {
    @Autowired
    private OrderDAL repository;

    @Autowired
    public OandaOrderService(MultiValueMap<String, String> headers,
                             RestTemplate restTemplate,
                             Urls urls) {
        super(headers, restTemplate, urls);
    }

    public Order postOrder(String accountId, OrderRequest orderRequest) {
        return this.restTemplate
                .exchange(fromPath(urls.ORDER_LIST_URL)
                                .build(accountId)
                                .getPath(),
                        POST,
                        new HttpEntity<>(Map.of("order", orderRequest), this.headers),
                        Order.class)
                .getBody();
    }

    /**
     * Returns list of all orders for the provided accountId.
     *
     * @return list of orders for provided accountId
     */
    public List<Order> getAllOrders(String accountId) {
        return this.restTemplate
                .exchange(fromPath(urls.ORDER_LIST_URL)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonOrderListWrapper.class)
                .getBody()
                .getOrders()
                .stream()
//                .map(order -> repository.upsert(order.getOrderId(), order))
                .collect(toList());
    }

    /**
     * Returns list of all pending orders for the provided accountId.
     *
     * @return list of pending orders for provided accountId
     */
    public List<Order> getPendingOrders(String accountId) {
        return this.restTemplate
                .exchange(fromPath(urls.ORDER_LIST_URL)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonOrderListWrapper.class)
                .getBody()
                .getOrders()
                .stream()
                .map(order -> repository.upsert(order.getOrderId(), order))
                .collect(toList());
    }

    public Order getOrder(String accountId, String orderId) {
        return this.restTemplate
                .exchange(fromPath(urls.SINGLE_ORDER_URL)
                                .build(accountId, orderId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        Order.class)
                .getBody();
    }

    public Order replaceOrder(String accountId, String existingOrderSpecifier, OrderRequest replacingOrder) {
        return this.restTemplate
                .exchange("boop",
                        PUT,
                        new HttpEntity<>(EMPTY, this.headers),
                        Order.class)
                .getBody();
    }

    public Order cancelOrder(String accountId, String orderSpecifier) {
        return this.restTemplate
                .exchange("boop",
                        PUT,
                        new HttpEntity<>(EMPTY, this.headers),
                        Order.class)
                .getBody();
    }

    public Order updateOrderClientExtensions(String accountId, String orderSpecifier) {
        return this.restTemplate
                .exchange("boop",
                        PUT,
                        new HttpEntity<>(EMPTY, this.headers),
                        Order.class)
                .getBody();
    }
}
