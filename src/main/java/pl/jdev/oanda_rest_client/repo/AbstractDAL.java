package pl.jdev.oanda_rest_client.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;

abstract class AbstractDAL<E extends AbstractEntity> implements GenericDAL<E> {
    @Autowired
    MongoTemplate template;
}
