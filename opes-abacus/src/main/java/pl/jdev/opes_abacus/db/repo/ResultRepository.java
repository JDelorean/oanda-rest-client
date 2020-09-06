package pl.jdev.opes_abacus.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jdev.opes_abacus.db.dto.ResultDto;

import java.util.UUID;

@Repository
public interface ResultRepository extends JpaRepository<ResultDto, UUID> {
}
