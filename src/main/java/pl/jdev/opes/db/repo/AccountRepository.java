package pl.jdev.opes.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jdev.opes.db.dto.AccountDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountDto, UUID> {
    Optional<AccountDto> findByExtId(String extId);

    List<AccountDto> findByIsSynced(Boolean isSynced);

    @Modifying
    @Query("UPDATE Account a SET a.isSynced = true WHERE a.id = :id")
    void setIsSyncedFlag(UUID id);

    @Modifying
    @Query("UPDATE Account a SET a.isSynced = false WHERE a.id = :id")
    void unsetIsSyncedFlag(UUID id);
}
