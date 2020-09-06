package pl.jdev.opes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jdev.opes_commons.rest.client.IntegrationClient;

public abstract class AbstractEntityController<E> {
    @Autowired
    IntegrationClient integrationClient;

}
