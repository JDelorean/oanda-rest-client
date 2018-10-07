package pl.jdev.opes.service.oanda_service.order;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes.config.Urls;
import pl.jdev.opes.domain.order.Order;
import pl.jdev.opes.domain.order.OrderRequest;
import pl.jdev.opes.repo.dal.OrderDAL;
import pl.jdev.opes.rest.json.wrapper.JsonOrderListWrapper;
import pl.jdev.opes.service.oanda_service.AbstractOandaService;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log4j2(topic = "CORE - Order")
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
                                .buildAndExpand(accountId)
                                .toString(),
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
                                .buildAndExpand(accountId)
                                .toString(),
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
                                .buildAndExpand(accountId)
                                .toString(),
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
                                .buildAndExpand(accountId, orderId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        Order.class)
                .getBody();
    }

    public Order replaceOrder(String accountId, String existingOrderSpecifier, OrderRequest replacingOrder) {
        return this.restTemplate
                .exchange(fromPath(null)
                                .buildAndExpand(accountId, existingOrderSpecifier)
                                .toString(),
                        PUT,
                        new HttpEntity<>(EMPTY, this.headers),
                        Order.class)
                .getBody();
    }

    public Order cancelOrder(String accountId, String orderSpecifier) {
        return this.restTemplate
                .exchange(fromPath(urls.CANCEL_ORDER_URL)
                                .buildAndExpand(accountId, orderSpecifier)
                                .toString(),
                        PUT,
                        new HttpEntity<>(EMPTY, this.headers),
                        Order.class)
                .getBody();
    }

    public Order updateOrderClientExtensions(String accountId, String orderSpecifier) {
        return this.restTemplate
                .exchange(fromPath(urls.ORDER_CLIENT_EXT_URL)
                                .buildAndExpand(accountId, orderSpecifier)
                                .toString(),
                        PUT,
                        new HttpEntity<>(EMPTY, this.headers),
                        Order.class)
                .getBody();
    }
}
