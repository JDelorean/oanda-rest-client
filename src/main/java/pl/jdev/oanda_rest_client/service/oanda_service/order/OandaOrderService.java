package pl.jdev.oanda_rest_client.service.oanda_service.order;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.OandaAuthConfig;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.order.Order;
import pl.jdev.oanda_rest_client.domain.order.OrderRequest;
import pl.jdev.oanda_rest_client.repo.OrderDAO;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonObjectListWrapper;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log
public class OandaOrderService extends AbstractOandaService<Order> {
    @Autowired
    private OrderDAO repository;

    @Autowired
    public OandaOrderService(MultiValueMap<String, String> headers,
                             RestTemplate restTemplate,
                             Urls urls) {
        super(headers, restTemplate, urls);
    }

    public Order postNewOrder(String accountId, OrderRequest orderRequest) {
        return this.restTemplate
                .exchange(fromPath(urls.ORDER_LIST_URL)
                                .build(accountId)
                                .getPath(),
                        POST,
                        new HttpEntity<>(Map.of("order", orderRequest), this.headers),
                        Order.class)
                .getBody();
    }

    public Object[] getAllOrders(String accountId) {
        List<Object> objects = this.restTemplate
                .exchange(fromPath(urls.ORDER_LIST_URL)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonObjectListWrapper.class)
                .getBody()
                .getObjectList();

        if (objects == null || objects.size() == 0) {
            return new Object[]{};
        } else {
            return Stream.of(objects)
                    .map(Order.class::cast)
                    .toArray();
        }

    }

    public Order[] getPendingOrders(String accountId) {
        return this.restTemplate
                .exchange(fromPath(urls.PENDING_ORDER_LIST_URL)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        Order[].class)
                .getBody();
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
