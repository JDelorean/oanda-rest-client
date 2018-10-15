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
import org.springframework.integration.router.HeaderValueRouter;
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
        LoggingHandler loggingHandler = new LoggingHandler(LoggingHandler.Level.TRACE);
        return loggingHandler;
    }

    @Bean
    MessageChannel pricingEventChannel() {
        return MessageChannels.publishSubscribe("pricingEventChannel")
                .interceptor(new WireTap(loggerChannel()))
                .get();
    }

    @Bean
    MessageChannel dataRequestChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    MessageChannel smaRequestChannel() {
        return MessageChannels.direct().get();
    }

    @ServiceActivator(inputChannel = "dataRequestChannel")
    @Bean
    HeaderValueRouter dataRequestRouter() {
        HeaderValueRouter router = new HeaderValueRouter("dataSource");
        router.setChannelMapping("sma", "smaRequestChannel");
        return router;
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
