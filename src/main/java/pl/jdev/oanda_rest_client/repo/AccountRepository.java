package pl.jdev.oanda_rest_client.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.jdev.oanda_rest_client.domain.account.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, ObjectId> {
    Account findByAccountId(String accountId);
}
