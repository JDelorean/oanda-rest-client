package pl.jdev.opes.service.oanda_service.instrument;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes.config.Urls;
import pl.jdev.opes.repo.dal.InstrumentDAL;
import pl.jdev.opes.rest.json.wrapper.JsonCandlestickListWrapper;
import pl.jdev.opes.service.IntegrationClient;
import pl.jdev.opes.service.oanda_service.AbstractOandaService;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;
import pl.jdev.opes_commons.rest.message.SMARequest;

import java.util.Collection;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Service
@Log4j2(topic = "CORE - Instrument")
public class OandaInstrumentService extends AbstractOandaService<Candlestick> {
    @Autowired
    private InstrumentDAL repository;
    @Autowired
    ApplicationContext ctx;
    @Autowired
    IntegrationClient integrationClient;

    @Autowired
    public OandaInstrumentService(MultiValueMap<String, String> headers,
                                  RestTemplate restTemplate,
                                  Urls urls) {
        super(headers, restTemplate, urls);
    }

    public Collection<Candlestick> getCandlestickList(String instrument,
                                                      CandlestickPriceType priceType,
                                                      CandlestickGranularity granularity,
                                                      int count) {
        return this.getCandlestickList(fromPath(urls.CANDLES)
                .queryParam("price", priceType)
                .queryParam("granularity", granularity)
                .queryParam("count", count)
                .buildAndExpand(instrument)
                .toString());
    }

    public Collection<Candlestick> getCandlestickList(String instrument,
                                                      CandlestickPriceType priceType,
                                                      CandlestickGranularity granularity,
                                                      String from,
                                                      String to) {
        return this.getCandlestickList(fromPath(urls.CANDLES)
                .queryParam("price", priceType)
                .queryParam("granularity", granularity)
                .queryParam("from", from)
                .queryParam("to", to)
                .buildAndExpand(instrument)
                .toString());
    }

    @SneakyThrows
    public Map<String, Double> getSma(String instrument,
                                      CandlestickPriceType priceType,
                                      CandlestickGranularity granularity,
                                      int count) {
        Collection<Candlestick> candles = this.getCandlestickList(instrument, priceType, granularity, count);
        return (Map<String, Double>) integrationClient.requestData(new SMARequest(candles)).getBody();
//        return ctx.getBean("smaCalculator", SMACalculator.class).calculate(candles);
    }

    @SneakyThrows
    public Map<String, Double> getSmaList(String instrument,
                                          CandlestickPriceType priceType,
                                          CandlestickGranularity granularity,
                                          int numOfSMAs,
                                          int numOfTimePeriods) {
        int count = numOfSMAs + numOfTimePeriods + 1;
        Collection<Candlestick> candles = this.getCandlestickList(instrument, priceType, granularity, count);
        return (Map<String, Double>) integrationClient.requestData(new SMARequest(candles)).getBody();
//        return ctx.getBean("smaCalculator", SMACalculator.class).calculate(candles, numOfSMAs, numOfTimePeriods);
    }

    private Collection<Candlestick> getCandlestickList(String uri) {
        return requireNonNull(this.restTemplate
                .exchange(uri,
                        GET,
                        new HttpEntity<>(EMPTY, this.headers),
                        JsonCandlestickListWrapper.class)
                .getBody())
                .getCandles();
    }
}
