package pl.jdev.opes.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import pl.jdev.opes.domain.pricing.Price;

import java.util.Collection;

@MessagingGateway
public interface PricingEventGateway {
    @Gateway(requestChannel = "pricingEventChannel")
    void send(Message<Collection<Price>> priceMessage);
}
