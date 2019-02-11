package pl.jdev.opes.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes.rest.exception.BadRequestException;
import pl.jdev.opes.rest.exception.NotFoundException;
import pl.jdev.opes.rest.exception.UnprocessableEntityException;
import pl.jdev.opes.service.AccountService;
import pl.jdev.opes.service.OrderService;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_commons.rest.json.OrderViews;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Log4j2
public class OrderController extends AbstractEntityController<Order> {

    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;


    @GetMapping
    @ResponseBody
    public Set<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    @ResponseBody
    public Order getOrder(@PathVariable(name = "orderId") final UUID orderId) throws NotFoundException {
        return orderService.getOrder(orderId);
    }

    @PostMapping
    @ResponseBody
    public Order createOrder(@JsonView(OrderViews.CoreCreate.class) @Valid @RequestBody final Order order) throws BadRequestException, UnprocessableEntityException, IOException {
        UUID accountId = order.getAccountId();
        if (!accountService.exists(accountId))
            throw new NotFoundException(String.format("No such account %s!", accountId));
        return orderService.createOrder(order);
    }

    @PostMapping("/{orderId}/cancel")
    @ResponseBody
    public Order cancelOrder(@Valid @PathVariable(name = "orderId") final UUID orderId) throws NotFoundException {
        return orderService.cancelOrder(orderId);
    }


    @PostMapping("/{orderId}/tag")
    public void tagOrder(@PathVariable(name = "orderId") final UUID orderId,
                         @RequestParam final List<String> tags)
            throws NotFoundException {
        tags.forEach(tag -> orderService.tagEntity(orderId, tag));
    }

    @PostMapping("/{orderId}/untag")
    public void untagOrder(@PathVariable(name = "orderId") final UUID orderId,
                           @RequestParam final List<String> tags)
            throws NotFoundException {
        tags.forEach(tag -> orderService.removeEntityTag(orderId, tag));
    }
}