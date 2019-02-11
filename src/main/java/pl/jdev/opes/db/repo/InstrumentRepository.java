package pl.jdev.opes.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jdev.opes.db.dto.InstrumentDto;

@Repository
public interface InstrumentRepository extends JpaRepository<InstrumentDto, Long> {
}
