package pl.jdev.oanda_rest_client.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.jdev.oanda_rest_client.domain.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}