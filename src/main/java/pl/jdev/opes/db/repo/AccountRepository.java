package pl.jdev.opes.db.repo;

import org.springframework.data.repository.CrudRepository;
import pl.jdev.opes.db.dto.AccountDto;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends CrudRepository<AccountDto, UUID> {
    List<AccountDto> findAll();
}
