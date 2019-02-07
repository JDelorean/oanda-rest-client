package pl.jdev.opes.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jdev.opes.db.dto.PriceCompositeKey;
import pl.jdev.opes.db.dto.PriceDto;

@Repository
public interface PriceRepository extends JpaRepository<PriceDto, PriceCompositeKey> {
}
