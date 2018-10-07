package pl.jdev.opes.service.oanda_service.pricing;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes.config.Urls;
import pl.jdev.opes.domain.pricing.Price;
import pl.jdev.opes.repo.dal.PricingDAL;
import pl.jdev.opes.rest.json.wrapper.JsonPricingListWrapper;
import pl.jdev.opes.service.oanda_service.AbstractOandaService;

import java.util.Collection;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.messaging.support.MessageBuilder.withPayload;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@PropertySource("classpath:default.user.preferences")
@Log4j2(topic = "CORE - Pricing")
public class OandaPricingService extends AbstractOandaService<Price> {
    @Autowired
    PricingDAL repository;
    @Autowired
    ApplicationContext ctx;

    @Autowired
    public OandaPricingService(MultiValueMap headers, RestTemplate restTemplate, Urls urls) {
        super(headers, restTemplate, urls);
    }

    public Collection<Price> getPrices(String accountId,
                                       Collection<String> instruments,
                                       String since,
                                       boolean includeUnitsAvailable,
                                       boolean includeHomeConversions) {
        Collection<Price> prices = this.restTemplate
                .exchange(fromPath(urls.PRICING_URL)
                                .queryParam("instruments", join(",", instruments))
                                .queryParam("since", since)
                                .queryParam("includeUnitsAvailable", includeUnitsAvailable)
                                .queryParam("includeHomeConversions", includeHomeConversions)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonPricingListWrapper.class)
                .getBody()
                .getPrices();
        return repository.upsertMulti(prices);
    }

    @Scheduled(cron = "#{${pricing.interval} ?: '0 0 0/1 * * ?'}")
    private void fetchPricesAtInterval() {
        String accountId = (String) ctx.getBean("accountId");
        Collection<String> currencyPairs = (Collection<String>) ctx.getBean("currencyPairs");
        log.info(format("Fetching prices for currency pairs %s...", join(", ", currencyPairs)));
        Collection<Price> prices = this.restTemplate
                .exchange(fromPath(urls.PRICING_URL)
                                .queryParam("instruments", join(",", currencyPairs))
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonPricingListWrapper.class)
                .getBody()
                .getPrices();
        log.info(format("Received prices %s", prices));
        repository.upsertMulti(prices);
        Message<Collection<Price>> priceMsg = withPayload(prices).build();
        ctx.getBean("pricingEventChannel", PublishSubscribeChannel.class).send(priceMsg);
    }
}
