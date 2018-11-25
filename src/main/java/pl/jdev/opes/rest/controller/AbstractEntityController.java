package pl.jdev.opes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jdev.opes_commons.domain.AbstractEntity;
import pl.jdev.opes_commons.rest.IntegrationClient;

public abstract class AbstractEntityController<E extends AbstractEntity> {
    @Autowired
    IntegrationClient integrationClient;
}
