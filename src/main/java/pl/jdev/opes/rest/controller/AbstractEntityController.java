package pl.jdev.opes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jdev.opes.domain.AbstractEntity;
import pl.jdev.opes.service.oanda_service.AbstractOandaService;

public abstract class AbstractEntityController<E extends AbstractEntity> {
    @Autowired
    AbstractOandaService<E> oandaService;
}
