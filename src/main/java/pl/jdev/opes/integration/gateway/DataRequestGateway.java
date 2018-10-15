package pl.jdev.opes.integration.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import pl.jdev.opes.integration.message.DataRequest;

@MessagingGateway
public interface DataRequestGateway {
    @Gateway(requestChannel = "dataRequestChannel")
    Object send(@Payload DataRequest dataRequest, @Header("requester") Class clazz);
}
