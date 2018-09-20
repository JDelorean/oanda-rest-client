package pl.jdev.opes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
public class ChannelConfig {
    @Bean
    MessageChannel loggerChannel() {
        return MessageChannels.direct().get();
    }

    @ServiceActivator(inputChannel = "loggerChannel")
    MessageHandler loggerHandler() {
        LoggingHandler loggingHandler = new LoggingHandler(LoggingHandler.Level.INFO);
        return loggingHandler;
    }

    @Bean
    MessageChannel pricingEventChannel() {
        return MessageChannels.publishSubscribe("pricingEventChannel")
                .interceptor(new WireTap(loggerChannel()))
                .get();
    }

    @Bean
    MessageChannel lastTransactionChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    DirectChannel orderChannel() {
        return new DirectChannel();
    }
}
