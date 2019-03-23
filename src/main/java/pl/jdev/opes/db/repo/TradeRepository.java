package pl.jdev.opes.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jdev.opes.db.dto.TradeDto;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TradeRepository extends JpaRepository<TradeDto, UUID> {
    Optional<TradeDto> findByExtId(String extId);

    boolean existsByExtId(String extId);
}
