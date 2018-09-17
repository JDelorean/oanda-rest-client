package pl.jdev.oanda_rest_client.service.oanda_service.pricing;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.oanda_rest_client.config.Urls;
import pl.jdev.oanda_rest_client.domain.pricing.Price;
import pl.jdev.oanda_rest_client.repo.dal.PricingDAL;
import pl.jdev.oanda_rest_client.rest.json.wrapper.JsonPricingListWrapper;
import pl.jdev.oanda_rest_client.service.oanda_service.AbstractOandaService;

import java.util.Collection;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log(topic = "CORE - Pricing")
public class OandaPricingService extends AbstractOandaService<Price> {
    @Autowired
    PricingDAL repository;

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
                                .queryParam("instruments", String.join(",", instruments))
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

}
