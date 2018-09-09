package pl.jdev.oanda_rest_client.rest.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.oanda_rest_client.domain.order.OrderRequest;
import pl.jdev.oanda_rest_client.service.oanda_service.order.OandaOrderService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts/{accountId}/orders")
@Log
public class OrderController {
    @Autowired
    OandaOrderService oandaOrderService;

    @PostMapping
    @ResponseBody
    public void createOrder(@Valid @PathVariable(name = "accountId") final String accountId,
                            @Valid @RequestBody final OrderRequest orderRequest) {
        oandaOrderService.postNewOrder(accountId, orderRequest);
    }

    @GetMapping
    @ResponseBody
    public Map<String, Object> getAllOrders(@Valid @PathVariable(name = "accountId") final String accountId) {
        return Map.of("orders", oandaOrderService.getAllOrders(accountId));
    }

    @GetMapping(value = "/pending")
    @ResponseBody
    public Map<String, Object> getAllPendingOrders(@Valid @PathVariable(name = "accountId") final String accountId) {
        return Map.of("pendingOrders", oandaOrderService.getPendingOrders(accountId));
    }

    @GetMapping(value = "/{orderSpecifier}")
    @ResponseBody
    public Map<String, Object> getOrder(@Valid @PathVariable(name = "accountId") final String accountId,
                                        @Valid @PathVariable(name = "orderSpecifier") final String orderSpecifier) {
        return Map.of("order", oandaOrderService.getOrder(accountId, orderSpecifier));
    }
}