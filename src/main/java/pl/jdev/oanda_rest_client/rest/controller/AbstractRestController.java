package pl.jdev.oanda_rest_client.rest.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;

public abstract class AbstractRestController<E extends AbstractEntity> {
    @Autowired
    MongoRepository<E, ObjectId> repository;
    @Autowired
    AbstractOandaService<E> oandaService;
}
