package pl.jdev.opes.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jdev.opes.db.dto.CandlestickCompositeKey;
import pl.jdev.opes.db.dto.CandlestickDto;

@Repository
public interface CandlesRepository extends JpaRepository<CandlestickDto, CandlestickCompositeKey> {
}
