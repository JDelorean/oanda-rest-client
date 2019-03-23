package pl.jdev.opes.db.repo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jdev.opes.db.dto.InstrumentDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstrumentRepository extends JpaRepository<InstrumentDto, Long> {
    Optional<InstrumentDto> findByName(String name);

    @Cacheable("trackedInstruments")
    List<InstrumentDto> findByIsTracked(Boolean isTracked);

    @Modifying
    @Query("UPDATE Instrument i SET i.isTracked = :isTracked WHERE i.id = :id")
    void setIsTrackedFlag(Long id, Boolean isTracked);
}
