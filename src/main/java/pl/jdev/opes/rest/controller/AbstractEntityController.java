package pl.jdev.opes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jdev.opes.service.oanda_service.AbstractOandaService;
import pl.jdev.opes_commons.domain.AbstractEntity;

public abstract class AbstractEntityController<E extends AbstractEntity> {
    @Autowired
    AbstractOandaService<E> oandaService;
}
