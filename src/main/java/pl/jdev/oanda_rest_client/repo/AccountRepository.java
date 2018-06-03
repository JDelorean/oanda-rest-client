package pl.jdev.oanda_rest_client.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.jdev.oanda_rest_client.domain.account.Account;

public interface AccountRepository extends MongoRepository<Account, ObjectId> {
    Account findByDocumentId(ObjectId id);

    Account findByAccountId(String accountId);
}
