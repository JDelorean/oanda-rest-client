package pl.jdev.opes.rest.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.message.CreateOrderAction;
import pl.jdev.opes_commons.rest.message.request.EntityDetailsRequest;
import pl.jdev.opes_commons.rest.message.response.JsonOrderListWrapper;
import pl.jdev.opes_commons.rest.message.response.JsonOrderWrapper;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

import static pl.jdev.opes_commons.rest.HttpHeaders.DATA_TYPE;
import static pl.jdev.opes_commons.rest.HttpHeaders.EVENT_TYPE;

@RestController
@RequestMapping("/orders")
@Log4j2
public class OrderController extends AbstractEntityController<Order> {

    @PostMapping
    public void createOrder(@Valid @RequestBody final CreateOrderAction createOrderAction) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(EVENT_TYPE, "createOrder");
        integrationClient.perform(createOrderAction, headers);
    }

    @GetMapping
    @ResponseBody
    public JsonOrderListWrapper getAllOrders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "order");
        return JsonOrderListWrapper.payloadOf(
                (Collection<Order>) integrationClient.requestData(
                        new EntityDetailsRequest(),
                        headers,
                        JsonOrderListWrapper.class
                ).getBody()
        );
    }

    @GetMapping("/{orderId}")
    @ResponseBody
    public JsonOrderWrapper getOrder(@PathVariable(name = "orderId") final UUID orderId) {
        //TODO: grab extId from db
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "order");
        return JsonOrderWrapper.payloadOf(
                (Order) integrationClient.requestData(
                        new EntityDetailsRequest(orderId, orderId.toString()),
                        headers,
                        JsonOrderWrapper.class
                ).getBody()
        );
    }
}