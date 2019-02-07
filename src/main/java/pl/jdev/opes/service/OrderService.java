package pl.jdev.opes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.jdev.opes.db.dto.AccountDto;
import pl.jdev.opes.db.dto.OrderDto;
import pl.jdev.opes.db.dto.mapper.OrderMapper;
import pl.jdev.opes.db.repo.OrderRepository;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes.rest.exception.RequestFailedException;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_commons.rest.IntegrationClient;
import pl.jdev.opes_commons.rest.message.request.OrderCancelRequest;
import pl.jdev.opes_commons.rest.message.request.OrderCreateRequest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService extends TaggableEntityService<OrderDto, UUID> {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private IntegrationClient integrationClient;
    @Autowired
    private OrderMapper mapper;

    public Set<Order> getAllOrders() {
        List<OrderDto> dtos = orderRepository.findAll();
        return dtos.stream()
                .map(mapper::convertToEntity)
                .collect(Collectors.toSet());
    }

    public Order getOrder(UUID orderId) throws NotFoundException {
        OrderDto dto = getOrderDto(orderId);
        return mapper.convertToEntity(dto);
    }

    private OrderDto getOrderDto(UUID orderId) throws NotFoundException {
        return orderRepository.findById(orderId).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Order createOrder(Order order) {
        OrderDto orderDto = orderRepository.save(mapper.convertToDto(order));
        AccountDto account = orderDto.getAccount();
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(order);
        orderCreateRequest.getHeaders().put("broker", account.getBrokerName().toString());
        ResponseEntity response = integrationClient.perform(orderCreateRequest);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RequestFailedException("Unable to execute request!");
        }
        order = (Order) response.getBody();
        return updateOrder(order);
    }

    @Transactional
    public Order updateOrder(Order orderUpdate) throws NotFoundException {
        if (!orderRepository.existsByExtId(orderUpdate.getExtId())) {
            throw new NotFoundException();
        }
        return mapper.convertToEntity(orderRepository.save(mapper.convertToDto(orderUpdate)));
    }

    public Order cancelOrder(UUID orderId) throws NotFoundException {
        OrderDto order = getOrderDto(orderId);
        AccountDto account = order.getAccount();
        OrderCancelRequest orderCancelRequest = new OrderCancelRequest(mapper.convertToEntity(order));
        orderCancelRequest.getHeaders().put("broker", account.getBrokerName());
        ResponseEntity response = integrationClient.perform(orderCancelRequest);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RequestFailedException("Unable to execute request!");
        }
        Order update = (Order) response.getBody();
        return updateOrder(update);
    }
}