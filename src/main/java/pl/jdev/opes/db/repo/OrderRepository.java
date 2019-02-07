package pl.jdev.opes.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jdev.opes.db.dto.OrderDto;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderDto, UUID> {
    OrderDto findByExtId(String extId);

    boolean existsByExtId(String extId);

}
