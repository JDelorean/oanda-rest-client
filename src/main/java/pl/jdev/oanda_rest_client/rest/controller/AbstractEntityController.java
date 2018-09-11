package pl.jdev.oanda_rest_client.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jdev.oanda_rest_client.domain.AbstractEntity;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;

public abstract class AbstractEntityController<E extends AbstractEntity> {
    @Autowired
    AbstractOandaService<E> oandaService;
}
