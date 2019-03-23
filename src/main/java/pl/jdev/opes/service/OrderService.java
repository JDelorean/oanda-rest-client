package pl.jdev.opes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jdev.opes.db.dto.OrderDto;
import pl.jdev.opes.db.dto.mapper.OrderMapper;
import pl.jdev.opes.db.repo.OrderRepository;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes.rest.exception.RequestFailedException;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.domain.instrument.Instrument;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_commons.domain.order.OrderState;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.IntegrationClient;
import pl.jdev.opes_commons.rest.json.CustomJsonBuilder;
import pl.jdev.opes_commons.rest.json.OrderViews;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static pl.jdev.opes_commons.rest.HttpHeaders.REQUEST_TYPE;
import static pl.jdev.opes_commons.rest.message.event.EventType.ORDER_UPDATED;
import static pl.jdev.opes_commons.rest.message.request.RequestType.CREATE_ORDER;
import static pl.jdev.opes_commons.rest.message.request.RequestType.ORDER_DETAILS;

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
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private ApplicationContext ctx;

    public Set<Order> getAllOrders(OrderState state) {
        List<OrderDto> dtos = orderRepository.findAll();
        return dtos.stream()
                .map(orderMapper::convertToEntity)
                .filter(order -> state.equals(null) ? true : order.getState() == state)
                .collect(Collectors.toSet());
    }

    public Order getOrder(UUID orderId) throws NotFoundException {
        OrderDto dto = orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
        return orderMapper.convertToEntity(dto);
    }

    public Order createOrder(Order newOrder) throws IOException, NotFoundException {
        Account account = accountService.getAccount(newOrder.getAccountId());
        Instrument instrument = instrumentService.getInstrument(newOrder.getInstrument());
        newOrder.setState(OrderState.CREATED);
        newOrder = insertOrder(newOrder);

        ObjectMapper mapper = new ObjectMapper();
        String json = new CustomJsonBuilder(mapper)
                .includeView(OrderViews.OandaCreate.class)
                .replace("accountId", account.getExtId())
                .buildForObject(newOrder);
        Message msg = MessageBuilder.withPayload(json)
                .setHeader(REQUEST_TYPE, CREATE_ORDER)
                .setHeader(HttpHeaders.SOURCE, account.getBroker())
                .build();
        ResponseEntity response = intC.request(msg);
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
        OrderDto dto = orderRepository.findByExtId(orderUpdate.getExtId()).orElseThrow(NotFoundException::new);
        orderUpdate.setAccountId(dto.getAccount().getId());
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

    @Transactional
    void syncOrdersForAccount(Account account) {
        Order dummy = new Order();
        String payload = "";
        try {
            payload = new CustomJsonBuilder(ctx.getBean(ObjectMapper.class))
                    .replace("accountId", account.getExtId())
                    .buildForObject(dummy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = MessageBuilder
                .withPayload(payload)
                .setHeader(REQUEST_TYPE, ORDER_DETAILS)
                .build();
        Arrays.stream((Order[]) intC
                .request(msg, Order[].class)
                .getBody())
                .peek(order -> order.setAccountId(account.getId()))
                .map(orderMapper::convertToDto)
                .forEach(orderRepository::save);
    }

    private Optional<Order> getOrderDetailsFromExternal(Order order) {
        String payload = "";
        try {
            payload = new CustomJsonBuilder(ctx.getBean(ObjectMapper.class))
                    .replace("accountId", accountService.getAccount(order.getAccountId()).getExtId())
                    .buildForObject(order);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = MessageBuilder.withPayload(payload)
                .setHeader(REQUEST_TYPE, ORDER_DETAILS)
//TODO:         .setHeader(HttpHeaders.SOURCE, order.)
                .build();
        order = (Order) intC.request(msg, Order.class)
                .getBody();
        return Optional.ofNullable(order);
    }

    @Scheduled(fixedRateString = "${opes.poll.order}")
    void pollOrders() {
        Map<UUID, Order> orders = orderRepository.findAll()
                .parallelStream()
                .filter(order -> order.getAccount().isSynced())
                .map(orderMapper::convertToEntity)
                .collect(Collectors.toMap(Order::getId, order -> order));
        orders.values()
                .parallelStream()
                .map(this::getOrderDetailsFromExternal)
                .map(Optional::get)
                .map(order -> {
                    UUID orderId = orderRepository
                            .findByExtId(order.getExtId())
                            .get()
                            .getId();
                    order.setId(orderId);
                    return order;
                })
                .filter(order -> !orders.get(order.getId()).equals(order))
                .map(this::updateOrder)
                .map(order -> MessageBuilder
                        .withPayload(order)
                        .setHeader(HttpHeaders.EVENT_TYPE, ORDER_UPDATED)
                        .build()
                )
                .forEach(intC::post);
    }
}