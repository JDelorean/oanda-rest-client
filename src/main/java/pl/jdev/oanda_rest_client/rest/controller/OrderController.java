package pl.jdev.oanda_rest_client.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.jdev.oanda_rest_client.repo.AccountRepository;
import pl.jdev.oanda_rest_client.repo.OrderRepository;

@RestController
@RequestMapping("/api/account/{accountUUID}/orders")
@Slf4j
public class OrderController {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	AccountRepository accountRepository;

	// BiFunction<UUID, UUID, Boolean> isForAccount = (orderUUID, accountUUID) ->
	// {return accountRepository.findOne(accountUUID).getOr};

	// @GetMapping
	// public ResponseEntity<Collection<Order>> getAllOrders(
	// @Valid @PathVariable(value = "accountUUID") final UUID accountUUID) {
	//// List<Order> orders = accountRepository.exists(accountUUID) ?
	// orderRepository.findAll().stream()
	//// .filter(order ->
	// order.isForAccount(accountUUID)).collect(Collectors.toList()) : null;
	//// return ResponseEntity.ok(orderRepository.findAll());
	// }
	//
	// @GetMapping("/{orderUUID}")
	// public ResponseEntity<?> getOrderById(@Valid @PathVariable(value =
	// "accountUUID") final UUID accountUUID, @Valid @PathVariable(value =
	// "orderUUID") final UUID orderUUID) {
	// Order order = accountRepository.exists(accountUUID) ?
	// orderRepository.findOne(orderUUID) : null;
	// return order == null ? ResponseEntity.notFound().build() :
	// ResponseEntity.ok(order);
	// }
	//
	// @PostMapping
	// public Order createOrder()
}