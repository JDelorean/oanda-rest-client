package pl.jdev.oanda_rest_client.service.oanda_service.order;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import pl.jdev.oanda_rest_client.config.OandaAuthConfig;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.order.Order;
import pl.jdev.oanda_rest_client.domain.order.OrderRequest;
import pl.jdev.oanda_rest_client.repo.OrderDAL;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;
import pl.jdev.oanda_rest_client.service.oanda_service.interceptor.RestLoggingInterceptor;

import java.util.Map;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log
public class OandaOrderService extends AbstractOandaService<Order> {
    @Autowired
    private OrderDAL repository;

    @Autowired
    public OandaOrderService(OandaAuthConfig oandaAuthConfig, Urls urls, RestLoggingInterceptor restLoggingInterceptor, MappingJackson2HttpMessageConverter messageConverter, RestTemplateBuilder restTemplateBuilder) {
        super(oandaAuthConfig, urls, restLoggingInterceptor, messageConverter, restTemplateBuilder);
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

    public Order[] getAllOrders(String accountId) {
        return this.restTemplate
                .exchange(fromPath(urls.ORDER_LIST_URL)
                                .build(accountId)
                                .getPath(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        Order[].class)
                .getBody();
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
