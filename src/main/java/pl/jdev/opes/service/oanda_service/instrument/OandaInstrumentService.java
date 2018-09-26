package pl.jdev.opes.service.oanda_service.instrument;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes.config.Urls;
import pl.jdev.opes.domain.instrument.Candlestick;
import pl.jdev.opes.domain.instrument.CandlestickGranularity;
import pl.jdev.opes.domain.instrument.CandlestickPriceType;
import pl.jdev.opes.repo.dal.InstrumentDAL;
import pl.jdev.opes.rest.json.wrapper.JsonCandlestickListWrapper;
import pl.jdev.opes.service.oanda_service.AbstractOandaService;

import java.util.Collection;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Service
@Log(topic = "CORE - Instrument")
public class OandaInstrumentService extends AbstractOandaService<Candlestick> {
    @Autowired
    private InstrumentDAL repository;

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

//    String instrument,
//    CandlestickPriceType priceType,
//    CandlestickGranularity granularity,
//    int count,
//    String from,
//    String to,
//    boolean smooth,
//    boolean includeFirst,
//    int dailyAlignment,
//    String alignmentTimezone,
//    DayOfWeek weeklyAlignment

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
