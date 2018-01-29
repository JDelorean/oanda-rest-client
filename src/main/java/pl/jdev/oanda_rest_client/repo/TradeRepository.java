package pl.jdev.oanda_rest_client.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.jdev.oanda_rest_client.domain.trade.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
}