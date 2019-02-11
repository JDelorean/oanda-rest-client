package pl.jdev.opes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.jdev.opes.db.dto.OrderDto;
import pl.jdev.opes.db.dto.mapper.OrderMapper;
import pl.jdev.opes.db.repo.OrderRepository;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes.rest.exception.RequestFailedException;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_commons.domain.order.OrderState;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.IntegrationClient;
import pl.jdev.opes_commons.rest.json.CustomJsonBuilder;
import pl.jdev.opes_commons.rest.json.OrderViews;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService extends TaggableEntityService<OrderDto, UUID> {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private IntegrationClient intC;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AccountService accountService;

    public Set<Order> getAllOrders() {
        List<OrderDto> dtos = orderRepository.findAll();
        return dtos.stream()
                .map(orderMapper::convertToEntity)
                .collect(Collectors.toSet());
    }

    public Order getOrder(UUID orderId) throws NotFoundException {
        OrderDto dto = getOrderDto(orderId);
        return orderMapper.convertToEntity(dto);
    }

    private OrderDto getOrderDto(UUID orderId) throws NotFoundException {
        return orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
    }

    public Order createOrder(Order newOrder) throws IOException {

        newOrder.setState(OrderState.CREATED);
        newOrder = insertOrder(newOrder);
        Account account = accountService.getAccount(newOrder.getAccountId());
        ObjectMapper mapper = new ObjectMapper();
        String json = new CustomJsonBuilder(mapper)
                .includeView(OrderViews.CoreCreate.class)
                .add("accountId", account.getExtId())
                .buildForObject(newOrder);
        Message msg = MessageBuilder.withPayload(json)
                .setHeader(HttpHeaders.ACTION_TYPE, "createOrder")
                .setHeader(HttpHeaders.SOURCE, account.getBroker())
                .build();
        ResponseEntity response = intC.perform(msg);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RequestFailedException("Unable to execute request!");
        }
        newOrder = (Order) response.getBody();
        return updateOrder(newOrder);
    }

    @Transactional
    public Order insertOrder(Order newOrder) {
        return orderMapper.convertToEntity(orderRepository.save(orderMapper.convertToDto(newOrder)));
    }

    @Transactional
    public Order updateOrder(Order orderUpdate) throws NotFoundException {
        if (!orderRepository.existsByExtId(orderUpdate.getExtId())) throw new NotFoundException();
        return orderMapper.convertToEntity(orderRepository.save(orderMapper.convertToDto(orderUpdate)));
    }

    public Order cancelOrder(UUID orderId) throws NotFoundException {
//        OrderDto order = getOrderDto(orderId);
//        AccountDto account = order.getAccount();
//        OrderCancelRequest orderCancelRequest = new OrderCancelRequest(orderMapper.convertToEntity(order));
//        orderCancelRequest.getHeaders().put("broker", account.getBrokerName());
//        ResponseEntity response = intC.perform(orderCancelRequest);
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            throw new RequestFailedException("Unable to execute request!");
//        }
//        Order update = (Order) response.getBody();
//        return updateOrder(update);
        return null;
    }
}